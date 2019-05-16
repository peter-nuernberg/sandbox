package fp101;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

public final class MyJListImpl implements MyJList {

    public static void main(String[] args) {

        final MyJList l1 = create(1, 2, 3);
        System.out.println(l1);
//        System.out.println(lastImp(l1));
//        System.out.println(lastFun(l1));
//        System.out.println(sum(l1));
//        System.out.println(sumFn(l1));
//        System.out.println(sumFn2(l1));
        System.out.println(filterImp(l1, MyJListImpl::isOdd));
        System.out.println(filterFn(l1, MyJListImpl::isOdd));
        System.out.println(filterFn2(l1, MyJListImpl::isOdd));
    }

    private static Boolean isOdd(Integer i) {
        return (i % 2) == 1;
    }

    private static Integer lastImp(MyJList l) {
//        throw new NotImplementedException();
        MyJList curr = l;
        MyJList prev = null;
        while (curr != nil) {
            prev = curr;
            curr = curr.getNext();
        }
        if (prev == null) {
            throw new NoSuchElementException("no last element in an empty list");
        }
        return prev.getValue();
    }

    private static Integer lastFun(MyJList l) {
//        throw new NotImplementedException();
        if (l == nil) {
            throw new NoSuchElementException("no last element in an empty list");
        }
        if (l.getNext() == nil) {
            return l.getValue();
        }
        return lastFun(l.getNext());
    }

    private static Integer sum(MyJList l) {
        Integer rval = 0;
        MyJList p = l;
        while (p != nil) {
            rval += p.getValue();
            p = p.getNext();
        }
        return rval;
    }

    private static Integer sumFn(MyJList l) {
        return sumAux(l, 0);
    }

    private static Integer sumAux(MyJList rest, Integer acc) {
        if (rest == nil) {
            return acc;
        }
        return sumAux(rest.getNext(), acc + rest.getValue());
    }

    private static Integer sumFn2(MyJList l) {
        return (l == nil) ? 0 : l.getValue() + sumFn2(l.getNext());
    }

    private static MyJList filterImp(MyJList l, Function<Integer, Boolean> pred) {
        MyJList rval = nil;
        MyJList p = l;
        while (p != nil) {
            if (pred.apply(p.getValue())) {
                rval = new MyJListImpl(p.getValue(), rval);
            }
            p = p.getNext();
        }
        return rval;
    }

    private static MyJList filterFn(MyJList l, Function<Integer, Boolean> pred) {
        if (l == nil) {
            return nil;
        }
        if (pred.apply(l.getValue())) {
            return new MyJListImpl(l.getValue(), filterFn(l.getNext(), pred));
        }
        return filterFn(l.getNext(), pred);
    }

    private static MyJList filterFn2(MyJList l, Function<Integer, Boolean> pred) {
        return filterFn2Aux(l, pred, nil);
    }

    private static MyJList filterFn2Aux(MyJList rest, Function<Integer, Boolean> pred, MyJList acc) {
        if (rest == nil) {
            return acc;
        }
        if (pred.apply(rest.getValue())) {
            return filterFn2Aux(rest.getNext(), pred, new MyJListImpl(rest.getValue(), acc));
        }
        return filterFn2Aux(rest.getNext(), pred, acc);
    }

    private static final MyJList nil = new MyJList() {
        @Override
        public Integer getValue() {
            throw new NoSuchElementException("cannot access value of nil");
        }
        @Override
        public MyJList getNext() {
            throw new NoSuchElementException("cannot access value of nil");
        }
        @Override
        public final String toString() {
            return "";
        }
    };

    private static MyJList create(Integer... values) {
        List<Integer> valueList = Arrays.asList(values);
        return Stream.iterate(valueList.size() - 1, (i) -> i - 1)
                .limit(valueList.size())
                .map(valueList::get)
                .reduce(nil, (acc, value) -> new MyJListImpl(value, acc), (l1, l2) -> l1);
    }

    private final Integer _value;

    private final MyJList _next;

    private MyJListImpl(Integer value, MyJList next) {
        super();
        _value = value;
        _next = next;
    }

    @Override
    public final Integer getValue() {
        return _value;
    }

    @Override
    public final MyJList getNext() {
        return _next;
    }

    @Override
    public final String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(_value.toString());
        if (_next != nil) {
            buf.append(" :: ");
            buf.append(_next.toString());
        }
        return buf.toString();
    }
}
