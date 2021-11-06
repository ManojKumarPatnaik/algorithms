package leetcode;

import java.util.*;
import java.util.stream.*;

public class p200 {
    static class s208 {//Implement Trie (Prefix Tree)
        class Trie {
            Trie[] nodes = new Trie[26];
            boolean word;

            public void insert(String w) {
                Trie node = this;
                for (char c : w.toCharArray())
                    node = (node.nodes[c - 'a'] = node.nodes[c - 'a'] == null ? new Trie() : node.nodes[c - 'a']);
                node.word = true;
            }

            public boolean search(String w) {
                Trie node = find(w);
                return node != null && node.word;
            }

            public boolean startsWith(String prefix) {return find(prefix) != null;}

            Trie find(String w) {
                Trie node = this;
                for (int i = 0; i < w.length() && node != null; node = node.nodes[w.charAt(i++) - 'a']) ;
                return node;
            }
        }
    }

    static class s225 {//Implement Stack using Queues
        class MyStack {
            Queue<Integer> q = new LinkedList<>();

            public void push(int x) {
                q.offer(x);
                for (int i = 0; i < q.size() - 1; i++)
                    q.offer(q.remove());
            }

            public int pop() {return q.remove();}

            public int top() {return q.peek();}

            public boolean empty() {return q.isEmpty();}
        }
    }

    static class s226 {//Invert Binary Tree
        public TreeNode invertTree(TreeNode root) {
            if (root == null)
                return null;
            TreeNode left = root.left;
            root.left = invertTree(root.right);
            root.right = invertTree(left);
            return root;
        }
    }

    static class s228 {//Summary Ranges
        public List<String> summaryRanges(int[] a) {
            List<String> r = new ArrayList<>();
            for (int i = 0, v; i < a.length; i++) {
                for (v = a[i]; i < a.length - 1 && a[i] + 1 == a[i + 1]; i++) ;
                r.add(v + (v != a[i] ? "->" + a[i] : ""));
            }
            return r;
        }
    }

    static class s231 {//Power of Two
        public boolean isPowerOfTwo(int n) {
            return n > 0 && (n & (n - 1)) == 0;
        }
    }

    static class s232 {//Implement Queue using Stacks
        class MyQueue {
            Stack<Integer> in = new Stack<>(), out = new Stack<>();

            public void push(int x) {in.push(x);}

            public int pop() {
                peek();
                return out.pop();
            }

            public int peek() {
                if (out.isEmpty())
                    for (; !in.isEmpty(); out.push(in.pop())) ;
                return out.peek();
            }

            public boolean empty() {return in.isEmpty() && out.isEmpty();}
        }
    }

    static class s235 {//Lowest Common Ancestor of a Binary Search Tree
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root.val < p.val && root.val < q.val)
                return lowestCommonAncestor(root.right, p, q);
            else if (root.val > p.val && root.val > q.val)
                return lowestCommonAncestor(root.left, p, q);
            else return root;
        }
    }

    static class s236 {//Lowest Common Ancestor of a Binary Tree
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || root == p || root == q)
                return root;
            TreeNode L = lowestCommonAncestor(root.left, p, q), R = lowestCommonAncestor(root.right, p, q);
            return L == null ? R : R == null ? L : root;
        }
    }

    static class s239 {//Sliding Window Maximum
        public int[] maxSlidingWindow(int[] a, int k) {
            int[] r = new int[a.length - k + 1];
            Deque<Integer> q = new ArrayDeque<>();
            for (int i = 0, j = 0; i < a.length; i++) {
                while (!q.isEmpty() && i - q.peek() + 1 > k)
                    q.poll();
                while (!q.isEmpty() && a[q.peekLast()] < a[i])
                    q.pollLast();
                q.offer(i);
                if (i >= k - 1)
                    r[j++] = a[q.peek()];
            }
            return r;
        }
    }

    static class s243 {//Shortest Word Distance
        public int shortestDistance(String[] words, String w1, String w2) {
            int j1 = -1, j2 = -1, r = Integer.MAX_VALUE;
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(w1))
                    j1 = i;
                else if (words[i].equals(w2))
                    j2 = i;
                if (j1 >= 0 && j2 >= 0)
                    r = Math.min(r, Math.abs(j2 - j1));
            }
            return r;
        }
    }

    static class s244 {//Shortest Word Distance II
        class WordDistance {
            final Map<String, List<Integer>> m = new HashMap<>();

            public WordDistance(String[] words) {
                IntStream.range(0, words.length).forEach(i -> m.computeIfAbsent(words[i], l -> new ArrayList<>()).add(i));
            }

            public int shortest(String w1, String w2) {
                int r = Integer.MAX_VALUE, i1 = 0, i2 = 0;
                for (List<Integer> l1 = m.get(w1), l2 = m.get(w2); i1 < l1.size() && i2 < l2.size(); )
                    if (l1.get(i1) < l2.get(i2))
                        r = Math.min(l2.get(i2) - l1.get(i1++), r);
                    else r = Math.min(l1.get(i1) - l2.get(i2++), r);
                return r;
            }
        }
    }
    static class s245 {//Shortest Word Distance III
        public int shortestWordDistance(String[] words, String w1, String w2) {
            int r = Integer.MAX_VALUE, i1 = words.length, i2 = -words.length;
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(w1)) {
                    if (w1.equals(w2))
                        i2 = i1;
                    i1 = i;
                } else if (words[i].equals(w2))
                    i2 = i;
                r = Math.min(r, Math.abs(i1 - i2));
            }
            return r;
        }
    }

    static class s246 {//Strobogrammatic Number
        public boolean isStrobogrammatic(String n) {
            Map<Character, Character> m = Map.of('0', '0', '1', '1', '6', '9', '8', '8', '9', '6');
            StringBuilder r = new StringBuilder(n.length());
            for (char c : n.toCharArray()) {
                if (!m.containsKey(c))
                    return false;
                else r.append(m.get(c));
            }
            return n.equals(r.reverse().toString());
        }

        public boolean isStrobogrammatic1(String a) {
            for (int i = 0, j = a.length() - 1; i <= j; i++, j--)
                if (!"00 11 88 696".contains(a.charAt(i) + "" + a.charAt(j)))
                    return false;
            return true;
        }
    }

    static class s249 {//Group Shifted Strings
        public List<List<String>> groupStrings(String[] strs) {
            Map<String, List<String>> m = new HashMap<>();
            for (String s : strs)
                m.computeIfAbsent(normalize(s.toCharArray()), l -> new ArrayList<>()).add(s);
            return new ArrayList<>(m.values());
        }

        String normalize(char[] s) {
            StringBuilder r = new StringBuilder(s.length);
            for (int i = 0, d = 'z' - s[0]; i < s.length; i++)
                r.append((char) ((s[i] + d) % 26));
            return r.toString();
        }
    }

    static class s251 {//Flatten 2D Vector
        class Vector2D {
            int r = 0, c = -1, v[][];

            public Vector2D(int[][] v) {
                this.v = v;
                move();
            }

            public int next() {
                int next = v[r][c];
                move();
                return next;
            }

            public boolean hasNext() {return r < v.length;}

            void move() {
                while (r < v.length && ++c >= v[r].length) {
                    r++;
                    c = -1;
                }
            }
        }
    }

    static class s252 {//Meeting Rooms
        public boolean canAttendMeetings(int[][] intervals) {
            Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));
            return IntStream.range(1, intervals.length).allMatch(i -> intervals[i - 1][1] <= intervals[i][0]);
        }
    }

    static class s253 {//Meeting Rooms II
        public int minMeetingRooms(int[][] intervals) {
            TreeMap<Integer, Integer> m = new TreeMap<>();
            for (int[] i : intervals) {
                m.put(i[0], m.getOrDefault(i[0], 0) + 1);
                m.put(i[1], m.getOrDefault(i[1], 0) - 1);
            }
            int r = 0, s = 0;
            for (Integer i : m.keySet()) {
                s += m.get(i);
                r = Math.max(r, s);
            }
            return r;
        }
    }

    static class s258 {//Add Digits
        public int addDigits(int n) {
            if (n == 0)
                return 0;
            return n % 9 == 0 ? 9 : n % 9;
        }
    }

    static class s261 {//Graph Valid Tree
        public boolean validTree(int n, int[][] edges) {
            List<List<Integer>> g = new ArrayList<>(n);
            IntStream.range(0, n).forEach(i -> g.add(new ArrayList<>()));
            for (int[] e : edges) {
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            Set<Integer> seen = new HashSet<>();
            dfs(0, g, seen);
            return seen.size() == n && edges.length == n - 1;
        }

        void dfs(int u, List<List<Integer>> g, Set<Integer> seen) {
            if (seen.add(u))
                g.get(u).forEach(v -> dfs(v, g, seen));
        }
    }

    static class s266 {//Palindrome Permutation
        public boolean canPermutePalindrome(String s) {
            BitSet bs = new BitSet();
            for (byte b : s.getBytes())
                bs.flip(b);
            return bs.cardinality() <= 1;
        }
    }

    static class s268 {//Missing Number
        public int missingNumber(int[] a) {
            return (1 + a.length) * a.length / 2 - Arrays.stream(a).sum();
        }
    }

    static class s270 {//Closest Binary Search Tree Value
        public int closestValue(TreeNode node, double target) {
            int r = node.val;
            while (node != null) {
                if (Math.abs(node.val - target) < Math.abs(target - r))
                    r = node.val;
                node = node.val < target ? node.right : node.left;
            }
            return r;
        }
    }

    static class s271 {//Encode and Decode Strings
        public class Codec {
            public String encode(List<String> strs) {
                StringBuilder r = new StringBuilder();
                for (String s : strs)
                    r.append(s.length()).append("/").append(s);
                return r.toString();
            }

            public List<String> decode(String s) {
                List<String> r = new ArrayList<>();
                for (int i = 0; i < s.length(); ) {
                    int slash = s.indexOf('/', i), len = Integer.parseInt(s.substring(i, slash));
                    i = slash + len + 1;
                    r.add(s.substring(slash + 1, i));
                }
                return r;
            }
        }
    }

    static class s278 {//First Bad Version
        public int firstBadVersion(int n) {
            int lo = 1, hi = n;
            while (lo + 1 < hi) {
                int m = lo + (hi - lo) / 2;
                if (isBadVersion(m))
                    hi = m;
                else lo = m;
            }
            return isBadVersion(lo) ? lo : hi;
        }

        boolean isBadVersion(int version) {return false;}//interface method
    }

    static class s281 {//Zigzag Iterator
        public class ZigzagIterator {
            Queue<Iterator<Integer>> q = new LinkedList<>();

            public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
                Stream.of(v1, v2).map(List::iterator).filter(Iterator::hasNext).forEach(q::offer);
            }

            public int next() {
                Iterator<Integer> it = q.poll();
                int r = it.next();
                if (it.hasNext())
                    q.offer(it);
                return r;
            }

            public boolean hasNext() {return !q.isEmpty();}
        }
    }

    static class s283 {//Move Zeroes
        public void moveZeroes(int[] a) {
            int i = 0;
            for (int j = 0; j < a.length; j++)
                if (a[j] != 0)
                    a[i++] = a[j];
            for (; i < a.length; a[i++] = 0) ;
        }
    }

    static class s286 {//Walls and Gates
        public void wallsAndGates(int[][] rooms) {
            Queue<int[]> q = new LinkedList<>();
            for (int i = 0; i < rooms.length; i++)
                for (int j = 0; j < rooms[0].length; j++)
                    if (rooms[i][j] == 0)
                        q.add(new int[]{i, j});
            for (int dirs[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; !q.isEmpty(); )
                for (int rc[] = q.poll(), i = 0; i < dirs.length; i++) {
                    int r = rc[0] + dirs[i][0], c = rc[1] + dirs[i][1];
                    if (0 <= r && r < rooms.length && 0 <= c && c < rooms[0].length && rooms[r][c] == Integer.MAX_VALUE) {
                        q.offer(new int[]{r, c});
                        rooms[r][c] = rooms[rc[0]][rc[1]] + 1;
                    }
                }
        }
    }

    static class s289 {
        public void gameOfLife(int[][] b) {
            for (int i = 0; i < b.length; i++)
                for (int j = 0; j < b[0].length; j++) {
                    int live = 0, newState = 0, oldState = b[i][j] & 1;
                    for (int x = -1; x <= 1; x++)
                        for (int y = -1; y <= 1; y++)
                            if (x != 0 || y != 0) {
                                int ni = i + x, nj = j + y;
                                if (0 <= ni && ni < b.length && 0 <= nj && nj < b[0].length)
                                    live += b[ni][nj] & 1;
                            }
                    if (oldState == 1)
                        newState = live == 2 || live == 3 ? 1 : 0;
                    else if (live == 3)
                        newState = 1;
                    b[i][j] = newState * 2 + oldState;
                }
            for (int i = 0; i < b.length; i++)
                for (int j = 0; j < b[0].length; j++)
                    b[i][j] >>= 1;
        }
    }

    static class s291 {//Word Pattern II
        public boolean wordPatternMatch(String p, String s) {return match(p, s, new HashMap<>());}

        boolean match(String p, String s, Map<Character, String> m) {
            if (p.isEmpty() && s.isEmpty())
                return true;
            if (p.isEmpty() || s.isEmpty())
                return false;
            char c = p.charAt(0);
            if (m.containsKey(c)) {
                String pre = m.get(c);
                return s.startsWith(pre) && match(p.substring(1), s.replaceFirst(pre, ""), m);
            }
            for (int i = 1; i <= s.length(); i++) {
                String pre = s.substring(0, i), rest = s.substring(i);
                if (!m.containsValue(pre)) {
                    m.put(c, pre);
                    if (match(p.substring(1), rest, m))
                        return true;
                    m.remove(c);
                }
            }
            return false;
        }
    }

    static class s293 {//Flip Game
        public List<String> generatePossibleNextMoves(String s) {
            List<String> r = new ArrayList<>();
            for (int i = 1; i < s.length(); i++)
                if (s.charAt(i - 1) == '+' && s.charAt(i) == '+')
                    r.add(s.substring(0, i - 1) + "--" + s.substring(i + 1));
            return r;
        }
    }
}
