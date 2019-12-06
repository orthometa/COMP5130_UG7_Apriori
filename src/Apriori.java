import java.util.*;

public class Apriori {
    private static List<String> data2DList = new ArrayList<>();
    private static double SUPPORT_PERCENT;

    public Apriori(List<String> transactionData, double support) {
        this.data2DList = transactionData;
        this.SUPPORT_PERCENT = support;
    }

    public void aprioriProcess() {
        //get the candidate itemsets of size 1
        Map<String, Integer> stepFrequentSetMap = new HashMap<>();
        System.out.println("\n===================== The 1 time scan of frequent set======================" + "\n");

        stepFrequentSetMap.putAll(getFrequentSets(findCandidateOneSets()));
        Set<String> stringSet = stepFrequentSetMap.keySet();

        for (String string : stringSet) {
            System.out.print("Frequent Set：");
            System.out.printf("%-8s", string);
            System.out.println(" Support:" + stepFrequentSetMap.get(string));
        }
        System.out.println("\nThe number of elements in frequent set：" + stringSet.size());

        int i = 1;

        //When frequent itemset is empty, get out of the loop
        while (stepFrequentSetMap != null && stepFrequentSetMap.size() > 0) {

            i++;

            System.out.println("\n=====================The " + i + " times scan of frequent set======================" + "\n");

            stepFrequentSetMap = getFrequentSets(getMinCandidate(stepFrequentSetMap));

            if (stepFrequentSetMap != null) {
                stringSet = stepFrequentSetMap.keySet();
                for (String string : stringSet) {
                    System.out.print("Frequent Set：");
                    System.out.printf("%-15s", string);
                    System.out.println(" Support:" + stepFrequentSetMap.get(string));
                }
                System.out.println("\nThe number of elements in frequent set：" + stringSet.size());
            }

        }
    }

    private static HashMap<String, Integer> findCandidateOneSets() {
        HashMap<String, Integer> resultSetMap = new HashMap<>();

        for (String dataList : data2DList) {
            String[] dataString = dataList.split(" ");
            for (String string : dataString) {
                string += " ";
                if (resultSetMap.get(string) == null) {
                    resultSetMap.put(string, 1);
                } else {
                    resultSetMap.put(string, resultSetMap.get(string) + 1);
                }
            }
        }
        return resultSetMap;
    }

    private static Map<String, Integer> getMinCandidate(Map<String, Integer> frequentMapSet) {
        Map<String, Integer> minCandidateMapSet = new HashMap<>();
        Set<String> frequentSet = frequentMapSet.keySet();

        //join operation
        for (String frequentItemList1 : frequentSet) {
            for (String frequentItemList2 : frequentSet) {
                String[] itemArray1 = frequentItemList1.split(" ");
                String[] itemArray2 = frequentItemList2.split(" ");

                String linkString = "";
                boolean canConnect = true;
                // k-1 items must be same
                // the last item of itemArray1 must less than the last item of itemArray2
                for (int i = 0; i < itemArray1.length - 1; i++) {
                    if (itemArray1[i].equals(itemArray2[i])) {
                        canConnect = false;
                        break;
                    }
                }
                if (canConnect && itemArray1[itemArray1.length - 1].compareTo(itemArray2[itemArray1.length - 1]) < 0) {
                    linkString = frequentItemList1 + itemArray2[itemArray2.length - 1] + " ";
                }

                //prune operation
                boolean hasInfrequentSubSet = false;
                if (linkString != "") {
                    String[] itemArray = linkString.split(" ");
                    for (int i = 0; i < itemArray.length; i++) {
                        String subString = "";
                        for (int j = 0; j < itemArray.length; j++) {
                            if (j != i) {
                                subString += itemArray[j] + " ";
                            }
                        }
                        if (frequentMapSet.get(subString) == null) {
                            hasInfrequentSubSet = true;
                            break;
                        }
                    }
                } else {
                    hasInfrequentSubSet = true;
                }
                if (!hasInfrequentSubSet) {
                    minCandidateMapSet.put(linkString, 0);
                }
            }
        }

        //calculate support number of candidate itemsets
        Set<String> minCandidateSet = minCandidateMapSet.keySet();
        for (String itemList : minCandidateSet) {
            String[] strings = itemList.split(" ");
            int num = 0;

            for (String data : data2DList) {
                List<String> dataList = Arrays.asList(data.split(" "));
                Boolean inDataList = true;
                for (int i = 0; i < strings.length; i++) {
                    if (!dataList.contains(strings[i])) {
                        inDataList = false;
                        break;
                    }
                }
                if (inDataList) {
                    minCandidateMapSet.put(itemList, minCandidateMapSet.get(itemList) + 1);
                }
            }
        }
        return minCandidateMapSet;
    }


    private static Map<String, Integer> getFrequentSets(Map<String, Integer> minCandidateMapSet) {
        if (minCandidateMapSet == null) {
            System.err.println("ItemSet is empty");
            return null;
        } else {
            Map<String, Integer> frequentMapSet = new HashMap<>();

            Set<String> minCandidateSet = minCandidateMapSet.keySet();


            Double SUPPORT = (data2DList.size() * SUPPORT_PERCENT);
            //Double SUPPORT = 5.0;
            System.out.println("Minimum-Support：" + SUPPORT + " Candidate pool size：" + minCandidateMapSet.size() + "\n");
            for (String itemListString : minCandidateSet) {

                if (minCandidateMapSet.get(itemListString) >= SUPPORT) {
                    frequentMapSet.put(itemListString, minCandidateMapSet.get(itemListString));
                }
            }
            if (frequentMapSet.size() == 0) {
                return null;
            } else {
                return frequentMapSet;
            }
        }
    }
}