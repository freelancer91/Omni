package omni.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.Predicate;

import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;

public abstract class ComparableOrderedSet<E extends Comparable<E>> implements OmniNavigableSet.OfRef<E>,Externalizable {
	Comparable<E>[] arr;
	int head;
	int tail;
	ComparableOrderedSet(){
		this.tail=-1;
	}
	ComparableOrderedSet(int head,Comparable<E>[] arr,int tail){
		this.head=head;
		this.arr=arr;
		this.tail=tail;
	}
	@Override public int size() {
		int tail;
		if((tail=this.tail+1)>0 && (tail-=this.head)<=0){
			tail+=arr.length;
		}
		return tail;
	}
	@Override public void clear() {
		int tail;
		if((tail=this.tail)!=-1) {
			final var arr=this.arr;
			final int head;
			if((head=this.head)>tail) {
				OmniArray.OfRef.nullifyRange(arr,tail,0);
				tail=arr.length-1;
			}
			OmniArray.OfRef.nullifyRange(arr, tail, head);
			this.tail=-1;
		}
	}
	@Override public boolean isEmpty() {
		return this.tail==-1;
	}
	@Override public OmniIterator.OfRef<E> iterator(){
		//TODO
		throw new omni.util.NotYetImplementedException();
	}
	@Override public OmniIterator.OfRef<E> descendingIterator(){
		//TODO
		throw new omni.util.NotYetImplementedException();
	}
	@Override public boolean removeIf(Predicate<? super E> filter) {
		//TODO
		throw new omni.util.NotYetImplementedException();
	}
	@Override public void forEach(Consumer<? super E> filter) {
		
	}
	@Override public boolean add(E key) {
		//TODO
		throw new omni.util.NotYetImplementedException();
	}
	@Override public void readExternal(ObjectInput input) throws IOException,ClassNotFoundException{
		//TODO
				throw new omni.util.NotYetImplementedException();
	}
	@Override public void writeExternal(ObjectOutput output) throws IOException{
		//TODO
				throw new omni.util.NotYetImplementedException();
		
	}
	public static class Ascending<E extends Comparable<E>> extends ComparableOrderedSet<E> implements Cloneable{
		public Ascending() {
			super();
		}
		public Ascending(int head,Comparable<E>[] arr,int tail){
			super(head,arr,tail);
		}
	}
	public static class Descending<E extends Comparable<E>> extends ComparableOrderedSet<E> implements Cloneable{
		public Descending() {
			super();
		}
		public Descending(int head,Comparable<E>[] arr,int tail){
			super(head,arr,tail);
		}
	}
}
