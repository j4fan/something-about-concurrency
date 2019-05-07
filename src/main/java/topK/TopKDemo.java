package topK;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class TopKDemo {
    private static final int TOP_K_SIZE = 5;

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,2,2,2,3,3,3,3,5,5,5,5,5,6,7,7,8,9,9,9,1,1,2,2,3,4,5};
        HashMap<Integer,Integer> map = new HashMap();
        IntStream.range(0,nums.length).forEach(i->{
            if(!map.containsKey(nums[i])){
                map.put(nums[i],1);
            }else{
                map.computeIfPresent(nums[i],(m,n)->n+1);
            }
        });

        PriorityQueue priorityQueue = new PriorityQueue(TOP_K_SIZE, Comparator.comparingInt(i->map.get(i)));
        map.entrySet().forEach(entry->{
            if(priorityQueue.size()<TOP_K_SIZE){
                priorityQueue.add(entry.getKey());
            }else{
                if(map.get(priorityQueue.peek())<entry.getValue()){
                    priorityQueue.poll();
                    priorityQueue.add(entry.getKey());
                }
            }
        });

        priorityQueue.forEach(i->{
            System.out.println(i.toString());
        });
    }
}
