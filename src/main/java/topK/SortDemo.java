package topK;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 1.返回第K大的数
 * 2.取中位数
 */


public class SortDemo {

    private static final int K_NUMBER = 11;

    /**
     * 返回第K大的值
     *
     * @param input
     * @param k
     * @return
     */
    private int getKthNumber(int[] input, int k) {
        if (k <= 0) {
            throw new RuntimeException("输入K值小于等于0,不合法");
        }
        if (input == null || input.length == 0) {
            throw new RuntimeException("输入数组为空");
        }
        int length = input.length;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, (Comparator.naturalOrder()));
        for (int i = 0; i < length; i++) {
            if (!maxHeap.contains(input[i])) {
                if (maxHeap.size() < k) {
                    maxHeap.offer(input[i]);
                } else if (maxHeap.peek() < input[i]) {
                    maxHeap.poll();
                    maxHeap.offer(input[i]);
                }
            }
        }
        if (maxHeap.size() < k) {
            throw new RuntimeException("输入K值过大，不合法");
        }
        return maxHeap.peek();
    }

    /**
     * 获取小于第K大数值的有序排列
     *
     * @return
     */
    private List getSortedListOfOtherNumbers(int[] input, int k) {
        if (input == null || input.length == 0) {
            throw new RuntimeException("数组为空");
        }
        List<Integer> sortedList = new ArrayList<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < input.length; i++) {
            if (input[i] < k) {
                queue.offer(input[i]);
            }
        }
        while (queue.size() > 0) {
            sortedList.add(queue.poll());
        }
        return sortedList;
    }


    /**
     * 获取有序列表的中位数
     *
     * @param list
     * @return
     */
    private int getMiddleNumberOfSortedList(List<Integer> list) {
        if (list == null || list.size() == 0) {
            throw new RuntimeException("列表为空");
        }
        int number = list.size() / 2 + 1;
        return list.get(number);
    }

    /**
     * test case
     *
     * @param args
     */
    public static void main(String[] args) {
        SortDemo demo = new SortDemo();
        int[] numbers = new int[]{7, 7, 7, 7, 1, 3, 4, 6, 3, 1, 1, 2, 2, 2, 3, 7, 2, 8, 9, 9, 4, 13, 30, 31, 31, 32, 33, 22, 3, 1, 13, 14, 15, 16};
        int kthLargestNumber = demo.getKthNumber(numbers, K_NUMBER);
        System.out.println("第" + K_NUMBER + "大的数是" + kthLargestNumber);
        List<Integer> list = demo.getSortedListOfOtherNumbers(numbers, kthLargestNumber);
        int middleNumber = demo.getMiddleNumberOfSortedList(list);
        System.out.println("剩余数组的中w位数为:" + middleNumber);
    }

}
