import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

    // Attention: comparable supported but comparator is not
    @SuppressWarnings("WeakerAccess")
    public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

        private static class Node<T> {
            final T value;

            Node<T> left = null;

            Node<T> right = null;

            Node(T value) {
                this.value = value;
            }
        }

        private Node<T> root = null;

        private int size = 0;

        @Override
        public boolean add(T t) {
            Node<T> closest = find(t);
            int comparison = closest == null ? -1 : t.compareTo(closest.value);
            if (comparison == 0) {
                return false;
            }
            Node<T> newNode = new Node<>(t);
            if (closest == null) {
                root = newNode;
            }
            else if (comparison < 0) {
                assert closest.left == null;
                closest.left = newNode;
            }
            else {
                assert closest.right == null;
                closest.right = newNode;
            }
            size++;
            return true;
        }

        boolean checkInvariant() {
            return root == null || checkInvariant(root);
        }

        private boolean checkInvariant(Node<T> node) {
            Node<T> left = node.left;
            if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
            Node<T> right = node.right;
            return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
        }

        @Override
        public boolean remove(Object o) {
            @SuppressWarnings("unchecked")
            T t = (T) o;
            Node<T> removing = find(t);

            if(removing != null && t.compareTo(removing.value) == 0) {
                int newSize = size - 1;

                Node<T> parent = findParent(removing, root);
                Node<T> left = removing.left;
                Node<T> right = removing.right;

                // Удаление вершины из потомков родителя
                if(parent != null) {
                    if(parent.left != null && parent.left.equals(removing)) parent.left = null;
                    else parent.right = null;
                } else {
                    root = null;
                }

                // Добавление всех вершин, которые являются потомками удаляемой вершины
                if(left != null) addTree(left);
                if(right != null) addTree(right);

                size = newSize;
                return true;
            }
            else return false;
        }

        private Node<T> findParent(Node<T> child, Node<T> start) {
            int comparison = child.value.compareTo(start.value);
            if (comparison == 0) return null;
            else if(comparison < 0) {
                if (start.left.equals(child)) return start;
                else return findParent(child, start.left);
            }
            else {
                if (start.right.equals(child)) return start;
                else return findParent(child, start.right);
            }
        }

        // Добавление всех вершин
        private void addTree(Node<T> extraRoot) {
            add(extraRoot.value);
            if(extraRoot.left != null) addTree(extraRoot.left);
            if(extraRoot.right != null) addTree(extraRoot.right);
        }

        @Override
        public boolean contains(Object o) {
            @SuppressWarnings("unchecked")
            T t = (T) o;
            Node<T> closest = find(t);
            return closest != null && t.compareTo(closest.value) == 0;
        }

        private Node<T> find(T value) {
            if (root == null) return null;
            return find(root, value);
        }

        private Node<T> find(Node<T> start, T value) {
            int comparison = value.compareTo(start.value);
            if (comparison == 0) {
                return start;
            }
            else if (comparison < 0) {
                if (start.left == null) return start;
                return find(start.left, value);
            }
            else {
                if (start.right == null) return start;
                return find(start.right, value);
            }
        }

        public class BinaryTreeIterator implements Iterator<T> {

            private Node<T> current = null;
            private int count = 0;
            private int temp = 0;

            private BinaryTreeIterator() {}

            private void findNext() {
                if (temp < size) {
                    count = 0;
                    iterator(root);
                    temp++;
                }
                else current = null;
            }

            private void iterator(Node<T> node) {
                if (node.left != null)
                    iterator(node.left);

                if (count == temp)
                    current = node;

                count++;

                if (node.right != null)
                    iterator(node.right);
            }

            @Override
            public boolean hasNext() {
                return temp < size;
            }

            @Override
            public T next() {
                findNext();
                if (current == null) throw new NoSuchElementException();
                return current.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        @NotNull
        @Override
        public Iterator<T> iterator() {
            return new BinaryTreeIterator();
        }

        @Override
        public int size() {
            return size;
        }


        @Nullable
        @Override
        public Comparator<? super T> comparator() {
            return null;
        }

        @NotNull
        @Override
        public SortedSet<T> subSet(T fromElement, T toElement) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        @Override
        public SortedSet<T> headSet(T toElement) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        @Override
        public SortedSet<T> tailSet(T fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public T first() {
            if (root == null) throw new NoSuchElementException();
            Node<T> current = root;
            while (current.left != null) {
                current = current.left;
            }
            return current.value;
        }

        @Override
        public T last() {
            if (root == null) throw new NoSuchElementException();
            Node<T> current = root;
            while (current.right != null) {
                current = current.right;
            }
            return current.value;
        }
    }