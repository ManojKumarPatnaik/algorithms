package leetcode;

import java.util.*;
import java.util.stream.*;

public class p1800{
    static class s1810{
        public int findShortestPath(GridMaster master){
            int N = 100, g[][] = new int[2 * N + 1][2 * N + 1], moves[][] = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}}, target[] = new int[]{-N, -N};
            char[] dirs = new char[]{'U', 'L', 'D', 'R'};
            IntStream.range(0, g.length).forEach(i -> Arrays.fill(g[i], -1));
            g[N][N] = 0;
            dfs(master, N, N, g, target, dirs, moves);
            boolean[][] seen = new boolean[2 * N + 1][2 * N + 1];
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
            for(q.offer(new int[]{N, N, 0}); !q.isEmpty(); ){
                int[] p = q.poll();
                if(p[0] == target[0] && p[1] == target[1])
                    return p[2];
                if(!seen[p[0]][p[1]]){
                    seen[p[0]][p[1]] = true;
                    for(int[] move : moves){
                        int nr = p[0] + move[0], nc = p[1] + move[1];
                        if(0 <= nr && nr <= 2 * N && 0 <= nc && nc <= 2 * N && !seen[nr][nc] && g[nr][nc] != -1)
                            q.offer(new int[]{nr, nc, p[2] + g[nr][nc]});
                    }
                }
            }
            return -1;
        }

        void dfs(GridMaster m, int r, int c, int[][] g, int[] target, char[] dirs, int[][] moves){
            if(m.isTarget()){
                target[0] = r;
                target[1] = c;
            }
            for(int i = 0; i < 4; i++){
                int nr = r + moves[i][0], nc = c + moves[i][1];
                if(m.canMove(dirs[i]) && g[nr][nc] == -1){
                    int val = m.move(dirs[i]);
                    g[nr][nc] = val;
                    dfs(m, nr, nc, g, target, dirs, moves);
                    m.move(dirs[(i + 2) % 4]);
                }
            }
        }

        interface GridMaster{
            boolean canMove(char direction);

            int move(char direction);

            boolean isTarget();
        }
    }

    static class s1814{//Count Nice Pairs in an Array
        public int countNicePairs(int[] a){
            Map<Integer, Integer> m = new HashMap<>();
            long r = 0;
            for(int n : a){
                int rev = 0;
                for(int i = n; i > 0; i /= 10)
                    rev = rev * 10 + i % 10;
                r += m.getOrDefault(n - rev, 0);
                m.put(n - rev, m.getOrDefault(n - rev, 0) + 1);
            }
            return (int) (r % 1_000_000_007);
        }
    }

    static class s1818{//Minimum Absolute Sum Difference
        public int minAbsoluteSumDiff(int[] a1, int[] a2){
            int sorted[] = Arrays.stream(a1).sorted().toArray(), max = Integer.MIN_VALUE;
            long sumDiff = 0;
            for(int i = 0; i < a2.length; i++){
                int diff = Math.abs(a1[i] - a2[i]), idx = Arrays.binarySearch(sorted, a2[i]);
                sumDiff += diff;
                idx = idx < 0 ? ~idx : idx;
                if(idx < sorted.length)
                    max = Math.max(max, diff - Math.abs(sorted[idx] - a2[i]));
                if(idx > 0)
                    max = Math.max(max, diff - Math.abs(sorted[idx - 1] - a2[i]));
            }
            return (int) ((sumDiff - max) % 1_000_000_007);
        }
    }

    static class s1838{//Frequency of the Most Frequent Element
        public int maxFrequency(int[] a, int k){
            Arrays.sort(a);
            int r = 0, i = 0, j = 0;
            for(long sum = 0; j < a.length; j++){
                sum += a[j];
                while(sum + k < (long) a[j] * (j - i + 1))
                    sum -= a[i++];
                r = Math.max(r, j - i + 1);
            }
            return r;
        }
    }

    static class s1846{//Maximum Element After Decreasing and Rearranging
        public int maximumElementAfterDecrementingAndRearranging(int[] a){
            Arrays.sort(a);
            int r = 0;
            for(int n : a)
                r = Math.min(r + 1, n);
            return r;
        }
    }

    static class s1850{//Minimum Adjacent Swaps to Reach the Kth Smallest Number
        public int getMinSwaps(String num, int k){
            char[] a = num.toCharArray(), b = a.clone();
            while(k-- > 0){
                int i = b.length - 2, j;
                for(; b[i] >= b[i + 1]; i--) ;
                for(j = i + 1; j < b.length && b[i] < b[j]; j++) ;
                swap(b, i, j - 1);
                for(int lo = i + 1, hi = b.length - 1; lo < hi; lo++, hi--)
                    swap(b, lo, hi);
            }
            int r = 0;
            for(int i = 0; i < a.length; i++)
                if(a[i] != b[i]){
                    int j = i;
                    for(; a[i] != b[j]; j++) ;
                    for(; j != i; j--, r++)
                        swap(b, j - 1, j);
                }
            return r;
        }

        void swap(char[] a, int i, int j){
            char t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }
}
