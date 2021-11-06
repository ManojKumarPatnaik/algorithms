package leetcode;

import java.util.*;
import java.util.stream.*;

public class p900 {
    static class s927 {//Three Equal Parts
        public int[] threeEqualParts(int[] a) {
            int ones = Arrays.stream(a).sum(), negative[] = {-1, -1};
            if (ones == 0)
                return new int[]{0, 2};
            if (ones % 3 != 0)
                return negative;
            int third = a.length - 1;
            for (int count = ones / 3; count > 0; count -= a[third--]) ;
            int i = find(a, 0, third + 1), j = find(a, i, third + 1);
            return i >= 0 && j >= 0 ? new int[]{i - 1, j} : negative;
        }

        int find(int[] a, int i, int j) {
            if (i == -1)
                return -1;
            for (; a[i] == 0; i++) ;
            for (; j < a.length; j++, i++)
                if (a[i] != a[j])
                    return -1;
            return i;
        }
    }

    static class s935 {//Knight Dialer
        public int knightDialer(int n) {
            int m[][] = {{4, 6}, {6, 8}, {7, 9}, {4, 8}, {0, 3, 9}, {}, {0, 1, 7}, {2, 6}, {1, 3}, {2, 4}}, counts[] = new int[10];
            for (Arrays.fill(counts, 1); n > 1; n--) {
                int[] nextCounts = new int[10];
                for (int i = 0; i <= 9; i++)
                    for (int d : m[i])
                        nextCounts[d] = (counts[i] + nextCounts[d]) % 1_000_000_007;
                counts = nextCounts;
            }
            return (int) (Arrays.stream(counts).asLongStream().sum() % 1_000_000_007);
        }
    }

    static class s947 {//Most Stones Removed with Same Row or Column
        public int removeStones(int[][] stones) {
            int[] uf = IntStream.range(0, stones.length).toArray();
            for (int i = 0; i < stones.length; i++)
                for (int j = i + 1; j < stones.length; j++)
                    if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) {
                        int x = find(i, uf), y = find(j, uf);
                        if (x != y)
                            uf[x] = y;
                    }
            return stones.length - (int) IntStream.range(0, stones.length).filter(x -> x == find(x, uf)).count();
        }

        int find(int x, int[] uf) {return x == uf[x] ? x : find(uf[x], uf);}
    }

    static class s948 {//Bag of Tokens
        public int bagOfTokensScore(int[] tokens, int power) {
            Arrays.sort(tokens);
            int r = 0, score = 0;
            for (int i = 0, j = tokens.length - 1; i <= j && score >= 0; )
                if (power >= tokens[i]) {
                    power -= tokens[i++];
                    r = Math.max(r, ++score);
                } else {
                    power += tokens[j--];
                    score--;
                }
            return r;
        }
    }

    static class s952 {//Largest Component Size by Common Factor
        public int largestComponentSize(int[] a) {
            int[] uf = IntStream.range(0, 100_001).toArray();
            for (int n : a)
                for (int d = 2; d <= Math.sqrt(n); d++)
                    if (n % d == 0)
                        for (Integer q : Arrays.asList(d, n / d)) {
                            int x = find(n, uf), y = find(q, uf);
                            if (x != y)
                                uf[x] = y;
                        }
            Map<Integer, Integer> m = new HashMap<>();
            int r = 0;
            for (int n : a) {
                int j = find(n, uf);
                m.put(j, m.getOrDefault(j, 0) + 1);
                r = Math.max(r, m.get(j));
            }
            return r;
        }

        int find(int x, int[] uf) {
            return x == uf[x] ? x : (uf[x] = find(uf[x], uf));
        }
    }

    static class s954 {//Array of Doubled Pairs
        public boolean canReorderDoubled(int[] a) {
            Map<Integer, Integer> m = new TreeMap<>();
            Arrays.stream(a).forEach(n -> m.put(n, m.getOrDefault(n, 0) + 1));
            for (int x : m.keySet())
                if (m.get(x) > 0) {
                    int x2 = x < 0 ? x / 2 : x * 2;
                    if (x < 0 && x % 2 != 0 || m.get(x) > m.getOrDefault(x2, 0))
                        return false;
                    m.put(x2, m.get(x2) - m.get(x));
                }
            return true;
        }
    }

    static class s955 {//Delete Columns to Make Sorted II
        public int minDeletionSize(String[] ss) {
            Set<Integer> deleteCols = new HashSet<>();
            for (int row = 1; row < ss.length; row++)
                for (int col = 0; col < ss[row].length(); col++) {
                    if (deleteCols.contains(col) || ss[row - 1].charAt(col) == ss[row].charAt(col))
                        continue;
                    if (ss[row - 1].charAt(col) > ss[row].charAt(col)) {
                        deleteCols.add(col);
                        row = 0;
                    }
                    break;
                }
            return deleteCols.size();
        }
    }

    static class s968 {//Binary Tree Cameras
        int r, leaf = 0, covered = 1, leaf_parent = 2;

        public int minCameraCover(TreeNode root) {return (dfs(root) == leaf ? 1 : 0) + r;}

        int dfs(TreeNode node) {
            if (node == null)
                return covered;
            int left = dfs(node.left), right = dfs(node.right);
            if (left == leaf || right == leaf) {
                r++;
                return leaf_parent;
            }
            return left == leaf_parent || right == leaf_parent ? covered : leaf;
        }
    }

    static class s982 {//Triples with Bitwise AND Equal To Zero
        public int countTriplets(int[] a) {
            int m[] = new int[1 << 16], r = 0;
            for (int i : a)
                for (int j : a)
                    m[i & j]++;
            for (int i : a)
                for (int j = 0; j < m.length; j++)
                    if ((i & j) == 0)
                        r += m[j];
            return r;
        }
    }

    static class s998 {//Maximum Binary Tree II
        public TreeNode insertIntoMaxTree(TreeNode root, int val) {
            if (root == null)
                return new TreeNode(val);
            if (root.val < val)
                return new TreeNode(val, root, null);
            root.right = insertIntoMaxTree(root.right, val);
            return root;
        }
    }

    static class s999 {//Available Captures for Rook
        public int numRookCaptures(char[][] b) {
            for (int i = 0; i < b.length; i++)
                for (int j = 0; j < b.length; j++)
                    if (b[i][j] == 'R')
                        return cap(1, 0, i, j, b) + cap(-1, 0, i, j, b) + cap(0, 1, i, j, b) + cap(0, -1, i, j, b);
            return 0;
        }

        int cap(int di, int dj, int i, int j, char[][] b) {
            for (i = i + di, j = j + dj; 0 <= i && i < b.length && 0 <= j && j < b.length && b[i][j] != 'B'; i += di, j += dj)
                if (b[i][j] == 'p')
                    return 1;
            return 0;
        }
    }
}
