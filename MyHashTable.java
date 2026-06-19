public class MyHashTable<K,V> {
    private static class Node<K,V>{
        final K key;
        V value;
        Node<K,V> next;

        Node(K key ,V value){
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V>[] table;
    private int size;
    private int capacity;

    public MyHashTable(int initcapac) {
        this.capacity = initcapac;
        this.table = (Node<K, V>[]) new Node[capacity];
        this.size = 0;
    }

    public MyHashTable() {
        this(16); // Стандартный начальный размер
    }

    private int hash(K key){
        if (key == null) return 0;
        return (key.hashCode())%capacity;
    }

    public int size() {
        return size;
    }

    public void put(K key, V value){
        int index = hash(key);
        Node<K,V> current = table[index];

        if (current == null){
            table[index] = new Node<>(key,value);
            size++;
            if ((double) size / capacity > 0.75 ) {
                resize();
            }
            return;
        }
        while(current != null){
            if(current.key.equals(key)){
                current.value = value;
                return;
            }
            if (current.next == null){
                break;
            }
            current = current.next;
        }
        current.next = new Node<>(key, value);
        size++;

        if ((double) size / capacity > 0.75 ){
            resize();
        }
    }

    public V get(K key){
        int index = hash(key);
        Node<K,V> current = table[index];

        if (current == null){
            return null;
        }
        while(current != null){
            if(current.key.equals(key)){
                return current.value;
            }
            current =current.next;
        }
        return null;
    }

    public V remove(K key){
        int index = hash(key);
        Node<K,V> current = table[index];
        Node<K,V> prev = null;

        while (current!=null){
            if(current.key.equals(key)){
                if (prev == null){
                    table[index]  = current.next;
                }
                else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }



    public void resize(){
        int newCapacity = this.capacity*2;

        Node<K,V>[] newTable = (Node<K, V>[])  new Node[newCapacity];

        for (int i = 0; i < capacity; i++) {

            Node<K,V> current = table[i];

            while(current != null){

                Node<K,V> nextNode = current.next;
                int newIndex = hash(current.key);
                current.next = newTable[newIndex];
                newTable[newIndex] = current;

                current = nextNode;
            }
        }
        this.table = newTable;
    }


}

