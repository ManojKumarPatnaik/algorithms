package leetcode;

import java.util.*;
import java.util.stream.*;

@SuppressWarnings("ALL")
public class p100 {
    static class s108 {//Convert Sorted Array to Binary Search Tree
        public TreeNode sortedArrayToBST(int[] a) {
            return bst(a, 0, a.length - 1);
        }

        TreeNode bst(int[] a, int lo, int hi) {
            if (lo > hi)
                return null;
            int m = (hi + lo) / 2;
            return new TreeNode(a[m], bst(a, lo, m - 1), bst(a, m + 1, hi));
        }
    }

    static class s121 {//Best Time to Buy and Sell Stock
        public int maxProfit(int[] prices) {
            int r = 0, min = prices[0];
            for (int p : prices) {
                min = Math.min(min, p);
                r = Math.max(r, p - min);
            }
            return r;
        }
    }

    static class s127 {//Word Ladder
        public int ladderLength(String start, String end, List<String> wordList) {
            Set<String> words = new HashSet<>(wordList), seen = new HashSet<>();
            Queue<String> q = new LinkedList<>();
            int r = 1;
            for (q.add(start), seen.add(start); !q.isEmpty(); r++)
                for (int size = q.size(); size > 0; size--) {
                    char[] a = q.poll().toCharArray();
                    for (int i = 0; i < a.length; i++) {
                        char old = a[i];
                        for (char c = 'a'; c <= 'z'; c++) {
                            a[i] = c;
                            String word = new String(a);
                            if (words.contains(word))
                                if (word.equals(end))
                                    return r + 1;
                                else if (seen.add(word))
                                    q.offer(word);
                        }
                        a[i] = old;
                    }
                }
            return 0;
        }
    }

    static class s139 {//Word Break
        public boolean wordBreak(String s, List<String> wordDict) {
            Set<String> words = new HashSet<>(wordDict);
            boolean[] dp = new boolean[s.length() + 1];
            dp[0] = true;
            for (int i = 1; i <= s.length(); i++)
                for (int j = 0; j < i; j++)
                    if (dp[j] && words.contains(s.substring(j, i))) {
                        dp[i] = true;
                        break;
                    }
            return dp[s.length()];
        }
    }

    static class s140 {//Word Break II
        public List<String> wordBreak(String s, List<String> words) {
            Node trie = new Node();
            for (String w : words) {
                Node node = trie;
                for (char c : w.toCharArray())
                    node = node.nodes[c - 'a'] == null ? (node.nodes[c - 'a'] = new Node()) : node.nodes[c - 'a'];
                node.isWord = true;
            }
            List<List<String>> r = new ArrayList<>();
            dfs(s, trie, new LinkedList<>(), r);
            return r.stream().map(l -> String.join(" ", l)).collect(Collectors.toList());
        }

        void dfs(String s, Node trie, LinkedList<String> words, List<List<String>> r) {
            if (s.isEmpty())
                r.add(new ArrayList<>(words));
            else for (int len = 1; len <= s.length(); len++) {
                String word = s.substring(0, len), rest = s.substring(len);
                Node node = trie;
                for (int i = 0; i < word.length() && node != null; i++)
                    node = node.nodes[word.charAt(i) - 'a'];
                if (node != null && node.isWord) {
                    words.add(word);
                    dfs(rest, trie, words, r);
                    words.removeLast();
                }
            }
        }

        class Node {
            Node[] nodes = new Node[26];
            boolean isWord;
        }
    }

    static class s151 {//Reverse Words in a String
        public String reverseWords(String s) {
            String[] words = s.trim().split("\\s+");
            StringBuilder r = new StringBuilder();
            for (int i = words.length - 1; i > 0; i--)
                r.append(words[i]).append(" ");
            return r.append(words[0]).toString();
        }
    }

    static class s153 {//Find Minimum in Rotated Sorted Array
        public int findMin(int[] a) {
            int lo = 0, hi = a.length - 1;
            while (lo < hi) {
                if (a[lo] < a[hi])
                    return a[lo];
                int m = (lo + hi) / 2;
                if (a[m] >= a[lo])
                    lo = m + 1;
                else hi = m;
            }
            return a[lo];
        }
    }

    static class s157 {//Read N Characters Given Read4
        public class Solution extends Reader4 {
            public int read(char[] buf, int n) {
                int i = 0, len = 4, j;
                for (char[] buf4 = new char[len]; i < n && len == 4; buf4 = new char[len])
                    for (j = 0, len = read4(buf4); i < n && j < len; i++, j++)
                        buf[i] = buf4[j];
                return i;
            }
        }

        class Reader4 {
            int read4(char[] buf4) {return 0;}
        }
    }

    static class s159 {//Longest Substring with At Most Two Distinct Characters
        public int lengthOfLongestSubstringTwoDistinct(String s) {
            Map<Character, Integer> m = new HashMap<>();
            int r = 0;
            for (int i = 0, j = 0; i < s.length(); i++) {
                m.put(s.charAt(i), i);
                if (m.size() > 2) {
                    int rmIdx = m.values().stream().mapToInt(n -> n).min().getAsInt();
                    m.remove(s.charAt(rmIdx));
                    j = rmIdx + 1;
                }
                r = Math.max(r, i - j + 1);
            }
            return r;
        }
    }

    static class s161 {//One Edit Distance
        public boolean isOneEditDistance(String s, String t) {
            if (Math.abs(s.length() - t.length()) > 1)
                return false;
            char[] a = s.toCharArray(), b = t.toCharArray();
            for (int i = 0; i < Math.min(a.length, b.length); i++)
                if (a[i] != b[i])
                    if (a.length == b.length)
                        return equals(a, b, i + 1, i + 1);
                    else if (a.length < b.length)
                        return equals(a, b, i, i + 1);
                    else return equals(a, b, i + 1, i);
            return Math.abs(s.length() - t.length()) == 1;
        }

        boolean equals(char[] a, char[] b, int i, int j) {
            for (; i < a.length; i++, j++)
                if (a[i] != b[j])
                    return false;
            return true;
        }
    }

    static class s163 {//Missing Ranges
        public List<String> findMissingRanges(int[] a, int lo, int hi) {
            List<String> r = new ArrayList<>();
            for (int i = 0; i < a.length; lo = a[i++] + 1)
                add(r, lo, a[i] - 1);
            add(r, lo, hi);
            return r;
        }

        void add(List<String> r, int lo, int hi) {
            if (lo == hi)
                r.add(String.valueOf(lo));
            else if (lo < hi)
                r.add(lo + "->" + hi);
        }
    }

    static class s167 {//Two Sum II - Input array is sorted (only one solution exists)
        public int[] twoSum(int[] a, int target) {
            int i = 0, j = a.length - 1;
            while (a[i] + a[j] != target)
                if (a[i] + a[j] > target)
                    j--;
                else i++;
            return new int[]{i + 1, j + 1};
        }
    }

    static class s170 {//Two Sum III - Data structure design
        class TwoSum {
            final List<Integer> list = new ArrayList<>();

            public void add(int n) {list.add(n);}

            public boolean find(int sum) {
                Set<Integer> s = new HashSet<>();
                for (Integer n : list)
                    if (s.contains(sum - n))
                        return true;
                    else s.add(n);
                return false;
            }
        }
    }

    static class s186 {//Reverse Words in a String II
        public void reverseWords(char[] s) {
            reverse(s, 0, s.length - 1);
            for (int i = 0, start = 0; i <= s.length; i++)
                if (i == s.length || s[i] == ' ') {
                    reverse(s, start, i - 1);
                    start = i + 1;
                }
        }

        void reverse(char[] s, int start, int end) {
            for (; start < end; start++, end--) {
                char t = s[start];
                s[start] = s[end];
                s[end] = t;
            }
        }
    }

    static class s190 {//Reverse Bits
        public int reverseBits(int n) {
            int r = 0;
            for (int i = 0; i < 32; i++, n >>= 1)
                r = (r << 1) | (n & 1);
            return r;
        }
    }
}
