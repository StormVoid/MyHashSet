import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyDoubleHashingSet implements Set {
    private int DEFAULT_ARRAY_SIZE = 17;
    private int numberOfElementsInStorage = 0;
    private Object storage[];

    MyDoubleHashingSet() {
        storage = new Object[DEFAULT_ARRAY_SIZE];
    }

    public MyDoubleHashingSet(int primeNumberSize) {
        if (checkIsItPrimeNumber(primeNumberSize)) {
            DEFAULT_ARRAY_SIZE = primeNumberSize;
            storage = new Object[DEFAULT_ARRAY_SIZE];
        } else {

            try {
                throw new Exception("Number must be prime");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public MyDoubleHashingSet(Set<?> set) {
        storage = new Object[set.size()];
        numberOfElementsInStorage = set.size();
        tableConverter(set.toArray(), numberOfElementsInStorage * 2 / DEFAULT_ARRAY_SIZE);
    }

    @Override
    public int size() {
        return numberOfElementsInStorage;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    //TODO
    @Override
    public boolean contains(Object elementByHashedIndex) {


        if (doesObjectEquals(elementByHashedIndex, storage[getObjectIndex(elementByHashedIndex)])) return true;

        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }


    //TODO
    @Override
    public boolean add(Object element) {

       if ( checkArrayOverflow()){
           System.out.println("*********");
           System.out.println("Сurrent array sieze: " + DEFAULT_ARRAY_SIZE);
           System.out.println("Amount of elements: " + numberOfElementsInStorage);

           Object tmpArray[] = storage;
           tableConverter(tmpArray, 2);
           System.out.println("*********");
       }



        int hash = getObjectIndex(element);

        if (!doesObjectEquals(element, storage[hash])) {
            storage[hash] = element;
            numberOfElementsInStorage++;

            System.out.println(Arrays.asList(storage));
            System.out.println("Hash from Object: " + element.hashCode());
            System.out.println("Hash in MyDoubleHashingSet: " + hashCodeIndexCounter(element.hashCode()));
            System.out.println("Amount of elements: " + numberOfElementsInStorage);
            System.out.println("________________________");

            return true;
        }


        System.out.println("Object exist, return false");

        return false;
    }


    @Override
    public boolean remove(Object element) {
        int hash = getObjectIndex(element);


        if (storage[hash] != null && doesObjectEquals(element, storage[hash])) {
            storage[hash] = null;
            numberOfElementsInStorage--;
            System.out.println("Removed '" + element + "' element with hash: " + hash);
            System.out.println(Arrays.asList(storage));
            return true;
        }


        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    @Override
    public void clear() {
        storage = new Object[DEFAULT_ARRAY_SIZE];
        numberOfElementsInStorage = 0;
    }

    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return new Object[0];
    }

    private boolean checkIsItPrimeNumber(int number) {
        BigInteger bigInteger = BigInteger.valueOf(number);
        return bigInteger.isProbablePrime((int) Math.log(number));
    }

    private boolean checkArrayOverflow() {
        if (DEFAULT_ARRAY_SIZE / 2 <= numberOfElementsInStorage) {
            return true;
        }
        return false;
    }

    private void tableConverter(Object tmpArray[], int newLargerArraySize) {
        for (int newArraySize = DEFAULT_ARRAY_SIZE * newLargerArraySize; ; newArraySize++) {
            System.out.println("New Array Size before natural check: " + newArraySize);
            if (checkIsItPrimeNumber(newArraySize)) {
                System.out.println("Намбер " + newArraySize + " из прайм)))))");
                System.out.println("New prime array size: " + newArraySize);
                DEFAULT_ARRAY_SIZE = newArraySize;
                storage = new Object[DEFAULT_ARRAY_SIZE];

                for (Object object : tmpArray) {
                    if (object != null) {
                        storage[getObjectIndex(object)] = object;
                    }
                }
                break;
            }
        }
    }



    private int hashCodeIndexCounter(int hashCOde) {


        return Math.abs(hashCOde % DEFAULT_ARRAY_SIZE);
    }

    private boolean doesObjectEquals(Object o, Object o2) {
        boolean equalsObjects;
        if (o2 != null) {
            equalsObjects = o.equals(o2);
        } else {
            equalsObjects = false;
        }

        return equalsObjects;


    }


    private int getObjectIndex(Object o) {
        int collisionHashcode = hashCodeIndexCounter(o.hashCode());
        int count = 0;

        System.out.println("Hash from collisionCheker: " + collisionHashcode);

        while (true) {
            if (storage[collisionHashcode] == null) {
                System.out.println("Empty cell: " + collisionHashcode + " -out from cycle");
                return collisionHashcode;


            } else {

                if (doesObjectEquals(o, storage[collisionHashcode])) {
                    System.out.println("Objects are equals " + collisionHashcode);
                    return collisionHashcode;
                } else {
                    collisionHashcode = (hashCodeIndexCounter(o.hashCode()) +
                            count * (1 + hashCodeIndexCounter(o.hashCode()) % (DEFAULT_ARRAY_SIZE / 2))) % DEFAULT_ARRAY_SIZE;
                    System.out.println("New object hash: " + collisionHashcode);

                    count++;
                }

            }

        }

    }

    @Override
    public String toString() {
        Object returnArray[] = new Object[numberOfElementsInStorage];

        int count = 0;
        for (Object obj : storage) {
            if (obj != null) {
                returnArray[count] = obj;
                count++;
            }
        }
        return Arrays.toString(returnArray);
    }
}

