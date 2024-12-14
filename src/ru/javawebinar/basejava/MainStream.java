package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        int[] values1 = {1, 2, 3, 3, 2, 3};
        int[] values2 = {9, 8};
        int[] values3 = {6, 2, 5, 3, 3, 4, 2, 9};
        int[] values4 = {8, 5, 6, 1, 1, 5, 7};
        System.out.println(minValue(values1));
        System.out.println(minValue(values2));
        System.out.println(minValue(values3));
        System.out.println(minValue(values4));

        List<Integer> integers1 = new ArrayList<>();
        integers1.add(1);
        integers1.add(2);
        integers1.add(1);
        integers1.add(3);
        integers1.add(7);

        List<Integer> integers2 = new ArrayList<>();
        integers2.add(2);
        integers2.add(2);
        integers2.add(1);
        integers2.add(3);
        integers2.add(7);

        System.out.println(oddOrEven(integers1));
        System.out.println(oddOrEven(integers2));

        System.out.println(oddOrEvenOneStream(integers1));
        System.out.println(oddOrEvenOneStream(integers2));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    private static List<Integer> oddOrEvenOneStream(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0));
        return map.get(map.get(false).size() % 2 != 0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Integer sum = integers.stream().reduce(0, Integer::sum);
        return integers.stream().filter(a -> sum % 2 == 0 && a % 2 != 0 ||
                        sum % 2 != 0 && a % 2 == 0)
                .collect(Collectors.toList());
    }
}