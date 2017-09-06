package com.juran.share;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static void basicStream() {
        System.out.println("Stream basic");
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        stream.forEach(System.out::println);

        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        stream.forEach(System.out::println);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
        stream.forEach(System.out::println);


        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
        System.out.println();

    }

    private static List<String> sampleList() {
        String[] strArray = new String[]{"a", "b", "c"};
        return Arrays.asList(strArray);
    }

    private static void toUpper() {
        System.out.println("String upper");
        List<String> wordList = sampleList();
        List<String> output = wordList.stream().
                map(String::toUpperCase).
                collect(Collectors.toList());
        Stream stream = Stream.of(output);
        stream.forEach(System.out::println);
        System.out.println();
    }

    private static void squ() {
        System.out.println("Integer Square");
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().
                map(n -> n * n).
                collect(Collectors.toList());
        squareNums.stream().forEach(System.out::println);
        System.out.println();
    }

    private static void flat() {
        System.out.println("Flat");
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
        outputStream.forEach(System.out::println);
        System.out.println();
    }

    private static void filter() {
        System.out.println("Filter");
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens =
                Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
        Stream.of(evens).forEach(System.out::println);
        System.out.println();
    }

    private static void peek() {
        System.out.println("Peek");
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
        System.out.println();
    }


    private static void optional() {
        System.out.println("Optional");

        String strA = " abcd ", strB = null;
        print(strA);
        print("");
        print(strB);

        System.out.println(getLength(strA));
        System.out.println(getLength(""));
        System.out.println(getLength(strB));

        System.out.println();
    }

    public static void print(String text) {
        Optional.ofNullable(text).ifPresent(System.out::println);
    }

    public static int getLength(String text) {
        return Optional.ofNullable(text).map(String::length).orElse(-1);
    }

    public static void doReduce() {
        System.out.println("Reduces");

        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println(concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(minValue);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(10, Integer::sum);
        System.out.println(sumValue);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        System.out.println(sumValue);

        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
        System.out.println(concat);

        System.out.println();
    }


    private static class Person {
        public int no;
        public int age;
        private String name;
        public Person (int no, String name) {
            this.no = no;
            this.name = name;
        }

        public Person (int no, String name, int age){
            this.no = no;
            this.name = name;
            this.age = age;
        }


        public String getName() {
            System.out.println(name);
            return name;
        }

        public int getAge(){
            return age;
        }
    }

    public static void limitAndSkip() {
        System.out.println("Limit And Skip");

        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().
                map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
        System.out.println();
    }

    public static void sort(){
        System.out.println("Sort");

        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
        personList2.stream().forEach(person -> {
            System.out.println(person.getName());
        });
        System.out.println();
    }

    public static void match(){
        System.out.println("Match");

        List<Person> persons = new ArrayList();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));
        boolean isAllAdult = persons.stream().
                allMatch(p -> p.getAge() > 18);
        System.out.println("All are adult? " + isAllAdult);
        boolean isThereAnyChild = persons.stream().
                anyMatch(p -> p.getAge() < 12);
        System.out.println("Any child? " + isThereAnyChild);
        System.out.println();
    }


    private static class PersonSupplier implements Supplier<Person> {
        private int index = 0;
        private Random random = new Random();
        @Override
        public Person get() {
            return new Person(index++, "StormTestUser" + index, random.nextInt(100));
        }
    }

    public static void generateStream() {
        System.out.println("Generate Stream");

        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);


        Stream.generate(new PersonSupplier()).
                limit(10).
                forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));


        Stream.iterate(0, n -> n + 3).limit(10). forEach(x -> System.out.print(x + " "));


        System.out.println();
    }

    public static void group(){
        System.out.println("Group");

        Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.groupingBy(Person::getAge));
        Iterator it = personGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
        }


        Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.partitioningBy(p -> p.getAge() < 18));
        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());


        System.out.println();
    }


    public static void main(String[] args) {
        basicStream();
        toUpper();
        squ();
        flat();
        filter();
        peek();
        optional();
        doReduce();
        limitAndSkip();
        sort();
        match();
        generateStream();
        group();
    }
}
