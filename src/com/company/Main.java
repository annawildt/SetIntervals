package com.company;

import java.util.*;


public class Main {
    private static Set<Set<Integer>> include = new HashSet<>();
    private static Set<Set<Integer>> exclude = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("FIRST EXAMPLE");
        firstExample();
        printResult(includeExclude(include, exclude));

        resetIncludeExclude();
        System.out.println("\n");

        System.out.println("SECOND EXAMPLE");
        secondExample();
        printResult(includeExclude(include, exclude));

        resetIncludeExclude();
        System.out.println("\n");

        System.out.println("THIRD EXAMPLE");
        thirdExample();
        printResult(includeExclude(include, exclude));
    }

    private static Set<Set<Integer>> includeExclude(Set<Set<Integer>> include, Set<Set<Integer>> exclude) {
        if (exclude.isEmpty()) {
            return include;
        } else {
            return removeExcludesFromInclude(include, exclude);
        }
    }

    private static Set<Set<Integer>> removeExcludesFromInclude(Set<Set<Integer>> include, Set<Set<Integer>> exclude) {
        for (Set<Integer> includeSet : include) {
            for (Set<Integer> excludeSet : exclude) {
                includeSet.removeAll(excludeSet);
            }
        }
        return checkIfWantSplit(include);
    }

    private static Set<Set<Integer>> checkIfWantSplit(Set<Set<Integer>> check) {
        Set<Set<Integer>> newResult = new HashSet<>();
        for (Set<Integer> set : check) {
            Integer[] setList = set.toArray(new Integer[set.size()]);

            int whereToSplit = splitIntoNewSets(setList);

            if (whereToSplit != 0) {
                Integer[] lowerThanSplit = new Integer[whereToSplit];
                Integer[] higherThanSplit = new Integer[setList.length - whereToSplit];

                System.arraycopy(setList, 0, lowerThanSplit, 0, lowerThanSplit.length);
                System.arraycopy(setList, whereToSplit, higherThanSplit, 0, higherThanSplit.length);

                newResult.add(new HashSet<>(createSet(lowerThanSplit[0], lowerThanSplit[lowerThanSplit.length - 1])));
                newResult.add(new HashSet<>(createSet(higherThanSplit[0], higherThanSplit[higherThanSplit.length - 1])));
            } else {
                newResult.add(set);
            }
        }
        return newResult;
    }

    private static int splitIntoNewSets(Integer[] setList) {
        for (int i = 0; i < setList.length; i++) {
            if (i != setList.length - 1) {
                if (!(setList[i + 1] == setList[i] + 1)) {
                    return i+1;
                }
            }
        }
        return 0;
    }

    private static void printResult(Set<Set<Integer>> result) {
        for (Set<Integer> set : result) {
            System.out.println("\n");
            for (Integer num : set) {
                System.out.print(" " + num + " ");
            }
        }
    }

    private static void firstExample() {
        include.add(createSet(10, 50));
        include.add(createSet(80, 120));

        exclude.add(createSet(40, 85));
    }

    private static void secondExample() {
        include.add(createSet(75, 90));
        include.add(createSet(95, 110));
    }

    private static void thirdExample() {
        include.add(createSet(0, 50));

        exclude.add(createSet(20, 30));
    }

    private static Set<Integer> createSet(int min, int max) {
        Set<Integer> createdSet = new HashSet<>();
        for (int i = min; i < max + 1; i++) {
            createdSet.add(i);
        }
        return createdSet;
    }

    private static void resetIncludeExclude() {
        include = new HashSet<>();
        exclude = new HashSet<>();
    }
}
