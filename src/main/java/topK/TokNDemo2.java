package topK;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 打印int数组中第11大的数，和除前十个大数以外的所有数字排序后的中位数，数组可能有重复的值。个数少于100个。
 * 可以在命令行执行，数组在命令行输入，或者在代码里都行。
 * 如：1  2  3 3 4 4 5 5 6 6 7 7 8 8 9 9 10 10  11 11 12 12 11 12  7 8 9 11 11 12 2 11 4 4
 * 第一大的数字是12， 第二大的是11，第三大的是10， 以此类推。
 * 除前十个大数以外的所有数字是 1 2 2
 */

public class TokNDemo2 {

    private static List<Integer> getRestOfToK(int[] nums,int k) {
        if (nums.length < 0 || k < 0 || k > nums.length) {
            throw new RuntimeException("不规范的输入");
        }
        PriorityQueue<Integer> topkQueue = new PriorityQueue(k);
        PriorityQueue<Integer> restQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int num : nums) {
            if (!topkQueue.contains(num)) {
                if (topkQueue.size() < k) {
                    topkQueue.add(num);
                } else {
                    if (topkQueue.peek() < num) {
                        restQueue.add(topkQueue.poll());
                        topkQueue.add(num);
                    } else {
                        restQueue.add(num);
                    }
                }
            } else {
                restQueue.add(num);
            }
        }
        List<Integer> result = new ArrayList<>();
        int topk = topkQueue.peek();
        while(restQueue.size()!=0){
            if(restQueue.peek()>=topk){
                restQueue.poll();
            }else{
                result.add(restQueue.poll());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,12,12,11,12,7,8,9,11,11,12,2,11,4,4};
        List<Integer> result = TokNDemo2.getRestOfToK(nums,9);
        System.out.println(result.stream().map((Integer t) -> t.toString()).collect(Collectors.joining(",")));
    }
}

