package omni.impl.seq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniList;
import omni.impl.AbstractOmniCollection;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.IllegalModification;
import omni.impl.MonitoredList;
import omni.impl.MonitoredObjectGen;
import omni.impl.StructType;
//TODO
//import omni.impl.set.BooleanSetImplOld;
//import omni.impl.set.ByteSetImplOld;
import omni.impl.set.CharOpenAddressHashSet;
import omni.impl.set.DoubleOpenAddressHashSet;
import omni.impl.set.FloatOpenAddressHashSet;
import omni.impl.set.IntOpenAddressHashSet;
import omni.impl.set.LongOpenAddressHashSet;
import omni.impl.set.RefOpenAddressHashSet;
import omni.impl.set.ShortOpenAddressHashSet;
import omni.util.OmniArray;
import omni.util.TestExecutorService;

public class SequenceEqualityTest{
    
    private static final SequenceInitParams[] LIST_INIT_PARAMS;
    private static final int[] SIZES;
    private static final int[] PACKED_BOOLEAN_SIZES;
    
    static {
        SIZES=IntStream.rangeClosed(0,10).toArray();
        IntStream.Builder packedSizeBuilder=IntStream.builder();
        for(int i=0;i<5;++i) {
            for(int j=-3;j<=3;++j) {
                int size=i*64+j;
                if(size>=0) {
                    packedSizeBuilder.accept(size);
                }
            }
        }
        PACKED_BOOLEAN_SIZES=packedSizeBuilder.build().toArray();
        
        Stream.Builder<SequenceInitParams> listInitParamsBuilder=Stream.builder();
        for(final var checkedType:CheckedType.values()){
          listInitParamsBuilder.accept(new SequenceInitParams(StructType.PackedBooleanArrList,DataType.BOOLEAN,checkedType,
                  OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR));
      }
      final int[] buffers=new int[]{0,1,2,3,4,60,61,62,63,64,65,66,67,68,124,125,126,127,128,129,130,131,132};
      for(final var pre0:buffers){
          for(final var pre1:buffers){
              if(pre1 > 0){
                  continue;
              }
              final var preAllocs=new int[]{pre0,pre1};
              for(final var post0:buffers){
                  for(final var post1:buffers){
                      if(post1 > 0){
                          continue;
                      }
                      final var postAllocs=new int[]{post0,post1};
                      for(final var checkedType:CheckedType.values()){
                          listInitParamsBuilder.accept(new SequenceInitParams(StructType.PackedBooleanArrSubList,
                                  DataType.BOOLEAN,checkedType,preAllocs,postAllocs));
                      }
                  }
              }
          }
      }
        for(var dataType:DataType.values()) {
            for(var checkedType:CheckedType.values()) {
                listInitParamsBuilder.accept(new SequenceInitParams(StructType.ArrList,dataType,checkedType,OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR));
                listInitParamsBuilder.accept(new SequenceInitParams(StructType.DblLnkList,dataType,checkedType,OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR));
                for(var pre0=0;pre0<=3;++pre0) {
                    for(var post0=0;post0<=3;++post0) {
                        int[] preAllocs0=new int[] {pre0};
                        int[] postAllocs0=new int[] {post0};
                        listInitParamsBuilder.accept(new SequenceInitParams(StructType.ArrSubList,dataType,checkedType,preAllocs0,postAllocs0));
                        listInitParamsBuilder.accept(new SequenceInitParams(StructType.DblLnkSubList,dataType,checkedType,preAllocs0,postAllocs0));
                        for(var pre1=0;pre1<=3;++pre1) {
                            for(var post1=0;post1<=3;++post1) {
                                int[] preAllocs1=new int[] {pre0,pre1};
                                int[] postAllocs1=new int[] {post0,post1};
                                listInitParamsBuilder.accept(new SequenceInitParams(StructType.ArrSubList,dataType,checkedType,preAllocs1,postAllocs1));
                                listInitParamsBuilder.accept(new SequenceInitParams(StructType.DblLnkSubList,dataType,checkedType,preAllocs1,postAllocs1));
                                for(var pre2=0;pre2<=3;++pre2) {
                                    for(var post2=0;post2<=3;++post2) {
                                        int[] preAllocs2=new int[] {pre0,pre1,pre2};
                                        int[] postAllocs2=new int[] {post0,post1,post2};
                                        listInitParamsBuilder.accept(new SequenceInitParams(StructType.ArrSubList,dataType,checkedType,preAllocs2,postAllocs2));
                                        listInitParamsBuilder.accept(new SequenceInitParams(StructType.DblLnkSubList,dataType,checkedType,preAllocs2,postAllocs2));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        LIST_INIT_PARAMS=listInitParamsBuilder.build().toArray(SequenceInitParams[]::new);
    }
    
    private static Object modifyVal(Object oldVal,DataType dataType) {
        switch(dataType) {
        case BOOLEAN:
            return !((boolean)oldVal);

        case BYTE:
            return (byte)((byte)oldVal+1);

        case CHAR:
            return (char)((char)oldVal+1);

        case SHORT:
            return (short)((short)oldVal+1);

        case INT:
            return (int)((int)oldVal+1);

        case LONG:
            return (long)((long)oldVal+1);

        case FLOAT:
            return (float)((float)oldVal+1);

        case DOUBLE:
            return (double)((double)oldVal+1);

        default:
            throw dataType.invalid();
        }
    }
    
    static enum EqualsOpponentTypes{
        VsNull{
            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,SequenceInitialization initSeq,int size,int period,int initVal) {
                return OmniArray.OfRef.DEFAULT_ARR;
            }
            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,SequenceInitialization initSeq,int size,int period,int initVal) {
                return new Object[] {null};
            }
        },
        VsSelf{
            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,SequenceInitialization initSeq,int size,int period,int initVal) {
                Stream.Builder<Object> builder=Stream.builder();
                var self=seq.getCollection();
                int thisSize=seq.size();
                builder.accept(self);
                var subList=self.subList(0,thisSize);
                builder.accept(subList);
                builder.accept(subList.subList(0,thisSize));
                switch(initParams.structType) {
                case ArrList:
                case DblLnkList:
                case PackedBooleanArrList:
                    break;
                case ArrSubList:
                case PackedBooleanArrSubList:
                {
                    var rootField=FieldAndMethodAccessor.prepareFieldForObj(self,"root");
                    var parentField=FieldAndMethodAccessor.prepareFieldForObj(self,"parent");
                    var rootOffsetField=FieldAndMethodAccessor.prepareFieldForObj(self,"rootOffset");
                    var root=(AbstractOmniCollection<?>)FieldAndMethodAccessor.getValue(rootField,self);
                    var rootOffset=FieldAndMethodAccessor.getIntValue(rootOffsetField,self);
                    if(rootOffset==0 && thisSize==root.size) {
                        builder.accept(root);
                    }
                    for(AbstractOmniCollection<?> curr=(AbstractOmniCollection<?>)self;;) {
                        AbstractOmniCollection<?> parent=(AbstractOmniCollection<?>)FieldAndMethodAccessor.getValue(parentField,curr);
                        if(parent==null || parent.size!=thisSize || rootOffset!=FieldAndMethodAccessor.getIntValue(rootOffsetField,parent)) {
                           break;   
                        }
                        builder.accept(curr=parent);
                    }
                }
                    break;
                case DblLnkSubList:
                {
                    var parentField=FieldAndMethodAccessor.prepareFieldForObj(self,"parent");
                    var rootField=FieldAndMethodAccessor.prepareFieldForObj(self,"root");
                    var headField=FieldAndMethodAccessor.prepareFieldForClass(self.getClass().getSuperclass(),"head");
                    var root=(AbstractOmniCollection<?>)FieldAndMethodAccessor.getValue(rootField,self);
                    var thisHead=FieldAndMethodAccessor.getValue(headField,self);
                    if(thisSize==root.size && thisHead==FieldAndMethodAccessor.getValue(headField,root)) {
                        builder.accept(root);
                    }
                    for(AbstractOmniCollection<?> curr=(AbstractOmniCollection<?>)self;;) {
                        AbstractOmniCollection<?> parent=(AbstractOmniCollection<?>)FieldAndMethodAccessor.getValue(parentField,curr);
                        if(parent==null || parent.size!=thisSize || thisHead!=FieldAndMethodAccessor.getValue(headField,parent)) {
                           break;   
                        }
                        builder.accept(curr=parent);
                    }
                    
                }
                    break;
                default:
                    throw initParams.structType.invalid();
                }
                return builder.build().toArray();
            }
            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,SequenceInitialization initSeq,int size,int period,int initVal) {
                Stream.Builder<Object> builder=Stream.builder();
                var self=seq.getCollection();
                int thisSize=seq.size();
                if(thisSize>0) {
                    var subList1=self.subList(0,thisSize-1);
                    builder.accept(subList1);
                    builder.accept(subList1.subList(0,subList1.size()));
                    
                    var subList2=self.subList(1,thisSize);
                    builder.accept(subList2);
                    builder.accept(subList2.subList(0,subList2.size()));
                    if(thisSize>1) {
                        var subList3=self.subList(1,thisSize-1);
                        builder.accept(subList3);
                        builder.accept(subList3.subList(0,subList3.size()));
                    }
                }
               
                switch(initParams.structType) {
                case ArrList:
                case DblLnkList:
                case PackedBooleanArrList:
                    break;
                case ArrSubList:
                case PackedBooleanArrSubList:
                {
                    var rootField=FieldAndMethodAccessor.prepareFieldForObj(self,"root");
                    var parentField=FieldAndMethodAccessor.prepareFieldForObj(self,"parent");
                    var rootOffsetField=FieldAndMethodAccessor.prepareFieldForObj(self,"rootOffset");
                    var root=(AbstractOmniCollection<?>)FieldAndMethodAccessor.getValue(rootField,self);
                    var rootOffset=FieldAndMethodAccessor.getIntValue(rootOffsetField,self);
                    if(rootOffset!=0 || thisSize!=root.size) {
                        builder.accept(root);
                    }
                    for(AbstractOmniCollection<?> curr=(AbstractOmniCollection<?>)self;;) {
                        AbstractOmniCollection<?> parent=(AbstractOmniCollection<?>)FieldAndMethodAccessor.getValue(parentField,curr);
                        if(parent==null) {
                           break;   
                        }
                        if(parent.size!=thisSize || rootOffset!=FieldAndMethodAccessor.getIntValue(rootOffsetField,parent)) {
                            builder.accept(parent);
                        }
                        curr=parent;
                    }
                    
                    if(thisSize>1 && thisSize!=root.size) {
                        if(rootOffset==0) {
                            builder.accept(((List<?>)root).subList(1,1+thisSize));
                        }else {
                            builder.accept(((List<?>)root).subList(rootOffset-1,rootOffset-1+thisSize));
                        }
                    }
                }
                    break;
                case DblLnkSubList:
                {
                    var parentField=FieldAndMethodAccessor.prepareFieldForObj(self,"parent");
                    var rootField=FieldAndMethodAccessor.prepareFieldForObj(self,"root");
                    var headField=FieldAndMethodAccessor.prepareFieldForClass(self.getClass().getSuperclass(),"head");
                    var root=(AbstractOmniCollection<?>)FieldAndMethodAccessor.getValue(rootField,self);
                    var thisHead=FieldAndMethodAccessor.getValue(headField,self);
                    if(thisSize!=root.size || thisHead!=FieldAndMethodAccessor.getValue(headField,root)) {
                        builder.accept(root);
                    }
                    for(AbstractOmniCollection<?> curr=(AbstractOmniCollection<?>)self;;) {
                        AbstractOmniCollection<?> parent=(AbstractOmniCollection<?>)FieldAndMethodAccessor.getValue(parentField,curr);
                        if(parent==null) {
                           break;   
                        }
                        if(parent.size!=thisSize || thisHead!=FieldAndMethodAccessor.getValue(headField,parent)) {
                            builder.accept(parent);
                        }
                        curr=parent;
                    }
                    
                }
                    break;
                default:
                    throw initParams.structType.invalid();
                }
                return builder.build().toArray();
            }
        },
        VsNonList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                return OmniArray.OfRef.DEFAULT_ARR;
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                Stream.Builder<Object> builder=Stream.builder();
                builder.accept("This is not a list");
                builder.accept(new HashSet<>());
                builder.accept(new HashMap<>());
                builder.accept(new Object());
                builder.accept(new Object[] {});
                builder.accept(new RefArrSeq.UncheckedStack<>());
                builder.accept(new BooleanArrSeq.UncheckedStack());
                builder.accept(new ByteArrSeq.UncheckedStack());
                builder.accept(new CharArrSeq.UncheckedStack());
                builder.accept(new ShortArrSeq.UncheckedStack());
                builder.accept(new IntArrSeq.UncheckedStack());
                builder.accept(new LongArrSeq.UncheckedStack());
                builder.accept(new FloatArrSeq.UncheckedStack());
                builder.accept(new DoubleArrSeq.UncheckedStack());
                builder.accept(new PackedBooleanArrSeq.UncheckedStack());
                builder.accept(new RefArrSeq.CheckedStack<>());
                builder.accept(new BooleanArrSeq.CheckedStack());
                builder.accept(new ByteArrSeq.CheckedStack());
                builder.accept(new CharArrSeq.CheckedStack());
                builder.accept(new ShortArrSeq.CheckedStack());
                builder.accept(new IntArrSeq.CheckedStack());
                builder.accept(new LongArrSeq.CheckedStack());
                builder.accept(new FloatArrSeq.CheckedStack());
                builder.accept(new DoubleArrSeq.CheckedStack());
                builder.accept(new PackedBooleanArrSeq.CheckedStack());
                builder.accept(new RefSnglLnkSeq.UncheckedStack<>());
                builder.accept(new BooleanSnglLnkSeq.UncheckedStack());
                builder.accept(new ByteSnglLnkSeq.UncheckedStack());
                builder.accept(new CharSnglLnkSeq.UncheckedStack());
                builder.accept(new ShortSnglLnkSeq.UncheckedStack());
                builder.accept(new IntSnglLnkSeq.UncheckedStack());
                builder.accept(new LongSnglLnkSeq.UncheckedStack());
                builder.accept(new FloatSnglLnkSeq.UncheckedStack());
                builder.accept(new DoubleSnglLnkSeq.UncheckedStack());
                builder.accept(new RefSnglLnkSeq.CheckedStack<>());
                builder.accept(new BooleanSnglLnkSeq.CheckedStack());
                builder.accept(new ByteSnglLnkSeq.CheckedStack());
                builder.accept(new CharSnglLnkSeq.CheckedStack());
                builder.accept(new ShortSnglLnkSeq.CheckedStack());
                builder.accept(new IntSnglLnkSeq.CheckedStack());
                builder.accept(new LongSnglLnkSeq.CheckedStack());
                builder.accept(new FloatSnglLnkSeq.CheckedStack());
                builder.accept(new DoubleSnglLnkSeq.CheckedStack());
                builder.accept(new RefSnglLnkSeq.UncheckedQueue<>());
                builder.accept(new BooleanSnglLnkSeq.UncheckedQueue());
                builder.accept(new ByteSnglLnkSeq.UncheckedQueue());
                builder.accept(new CharSnglLnkSeq.UncheckedQueue());
                builder.accept(new ShortSnglLnkSeq.UncheckedQueue());
                builder.accept(new IntSnglLnkSeq.UncheckedQueue());
                builder.accept(new LongSnglLnkSeq.UncheckedQueue());
                builder.accept(new FloatSnglLnkSeq.UncheckedQueue());
                builder.accept(new DoubleSnglLnkSeq.UncheckedQueue());
                builder.accept(new RefSnglLnkSeq.CheckedQueue<>());
                builder.accept(new BooleanSnglLnkSeq.CheckedQueue());
                builder.accept(new ByteSnglLnkSeq.CheckedQueue());
                builder.accept(new CharSnglLnkSeq.CheckedQueue());
                builder.accept(new ShortSnglLnkSeq.CheckedQueue());
                builder.accept(new IntSnglLnkSeq.CheckedQueue());
                builder.accept(new LongSnglLnkSeq.CheckedQueue());
                builder.accept(new FloatSnglLnkSeq.CheckedQueue());
                builder.accept(new DoubleSnglLnkSeq.CheckedQueue());
                builder.accept(new RefArrDeq<>());
                builder.accept(new BooleanArrDeq());
                builder.accept(new ByteArrDeq());
                builder.accept(new CharArrDeq());
                builder.accept(new ShortArrDeq());
                builder.accept(new IntArrDeq());
                builder.accept(new LongArrDeq());
                builder.accept(new FloatArrDeq());
                builder.accept(new DoubleArrDeq());
                builder.accept(new PackedBooleanArrDeq());
                builder.accept(new RefArrDeq.Checked<>());
                builder.accept(new BooleanArrDeq.Checked());
                builder.accept(new ByteArrDeq.Checked());
                builder.accept(new CharArrDeq.Checked());
                builder.accept(new ShortArrDeq.Checked());
                builder.accept(new IntArrDeq.Checked());
                builder.accept(new LongArrDeq.Checked());
                builder.accept(new FloatArrDeq.Checked());
                builder.accept(new DoubleArrDeq.Checked());
                builder.accept(new PackedBooleanArrDeq.Checked());
                //TODO
                //builder.accept(new BooleanSetImplOld());
                //builder.accept(new ByteSetImplOld());
                builder.accept(new CharOpenAddressHashSet());
                builder.accept(new ShortOpenAddressHashSet());
                builder.accept(new IntOpenAddressHashSet());
                builder.accept(new LongOpenAddressHashSet());
                builder.accept(new FloatOpenAddressHashSet());
                builder.accept(new DoubleOpenAddressHashSet());
                builder.accept(new RefOpenAddressHashSet<>());
                //TODO
                //builder.accept(new BooleanSetImplOld.Checked());
                //builder.accept(new ByteSetImplOld.Checked());
                builder.accept(new CharOpenAddressHashSet.Checked());
                builder.accept(new ShortOpenAddressHashSet.Checked());
                builder.accept(new IntOpenAddressHashSet.Checked());
                builder.accept(new LongOpenAddressHashSet.Checked());
                builder.accept(new FloatOpenAddressHashSet.Checked());
                builder.accept(new DoubleOpenAddressHashSet.Checked());
                builder.accept(new RefOpenAddressHashSet.Checked<>());
                return builder.build().toArray();
            }
            
        },
        VsStandardJavaList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                Stream.Builder<Object> builder=Stream.builder();
                var thisList=seq.getCollection();
                builder.accept(new ArrayList<>(thisList));
                builder.accept(new LinkedList<>(thisList));
                builder.accept(new Vector<>(thisList));
                builder.accept(new ArrayList<>(thisList).subList(0,size));
                builder.accept(new LinkedList<>(thisList).subList(0,size));
                builder.accept(new Vector<>(thisList).subList(0,size));
                builder.accept(new ArrayList<>(thisList).subList(0,size).subList(0,size));
                builder.accept(new LinkedList<>(thisList).subList(0,size).subList(0,size));
                builder.accept(new Vector<>(thisList).subList(0,size).subList(0,size));
                ArrayList<Object> arrListRoot=new ArrayList<>(10+size);
                for(int i=0;i<5;++i) {
                    arrListRoot.add(new Object());
                }
                arrListRoot.addAll(thisList);
                for(int i=0;i<5;++i) {
                    arrListRoot.add(new Object());
                }
                builder.accept(arrListRoot.subList(5,5+size));
                builder.accept(new LinkedList<>(arrListRoot).subList(5,5+size));
                builder.accept(new Vector<>(arrListRoot).subList(5,5+size));
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                Stream.Builder<Object> builder=Stream.builder();
                //add one
                for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                    @SuppressWarnings("unchecked")
                    List<Object> asList=(List<Object>)val;
                    asList.add(new Object());
                    builder.accept(asList);
                }
                if(size>0) {
                    //subtract one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        @SuppressWarnings("unchecked")
                        List<Object> asList=(List<Object>)val;
                        asList.remove(size-1);
                        builder.accept(asList);
                    }
                    if(initParams.collectionType!=DataType.REF) {
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                          @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                          asList.set(size-1,modifyVal(asList.get(size-1),initParams.collectionType));
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                            asList.set(0,modifyVal(asList.get(0),initParams.collectionType));
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                                asList.set(size/2,modifyVal(asList.get(size/2),initParams.collectionType));
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                   
                    //change the type of one at the end
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                      @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                        asList.set(size-1,new Object());
                        builder.accept(asList);
                    }
                    if(size>1) {
                      //change the type of one at the beginning
                      for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                          asList.set(0,new Object());
                          builder.accept(asList);
                      }
                      if(size>2) {
                        //change the type of one in the middle
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                          @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                            asList.set(size/2,new Object());
                            builder.accept(asList);
                        }
                      }
                    }
                }
                return builder.build().toArray();
                
            }
            
        },
        VsRefList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                Stream.Builder<Object> builder=Stream.builder();
                var thisList=seq.getCollection();
                {
                    builder.accept(new RefArrSeq.UncheckedList<>(thisList));
                    builder.accept(new RefArrSeq.CheckedList<>(thisList));
                    builder.accept(new RefDblLnkSeq.UncheckedList<>(thisList));
                    builder.accept(new RefDblLnkSeq.CheckedList<>(thisList));
                    builder.accept(new RefArrSeq.UncheckedList<>(thisList).subList(0,size));
                    builder.accept(new RefArrSeq.CheckedList<>(thisList).subList(0,size));
                    builder.accept(new RefDblLnkSeq.UncheckedList<>(thisList).subList(0,size));
                    builder.accept(new RefDblLnkSeq.CheckedList<>(thisList).subList(0,size));
                }
                {
                    var uncheckedArrList=new RefArrSeq.UncheckedList<>();
                    for(int i=0;i<5;++i) {
                        uncheckedArrList.add(new Object());
                    }
                    uncheckedArrList.addAll(thisList);
                    for(int i=0;i<5;++i) {
                        uncheckedArrList.add(new Object());
                    }
                    builder.accept(uncheckedArrList.subList(5,5+size));
                    builder.accept(new RefArrSeq.CheckedList<>(uncheckedArrList).subList(5,5+size));
                    builder.accept(new RefDblLnkSeq.UncheckedList<>(uncheckedArrList).subList(5,5+size));
                    builder.accept(new RefDblLnkSeq.CheckedList<>(uncheckedArrList).subList(5,5+size));
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                Stream.Builder<Object> builder=Stream.builder();
                //add one
                for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                    @SuppressWarnings("unchecked")
                    List<Object> asList=(List<Object>)val;
                    asList.add(new Object());
                    builder.accept(asList);
                }
                if(size>0) {
                    //subtract one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        @SuppressWarnings("unchecked")
                        List<Object> asList=(List<Object>)val;
                        asList.remove(size-1);
                        builder.accept(asList);
                    }
                    //modify one at the end
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                      @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                        asList.set(size-1,new Object());
                        builder.accept(asList);
                    }
                    if(size>1) {
                      //modify one at the beginning
                      for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                          asList.set(0,new Object());
                          builder.accept(asList);
                      }
                      if(size>2) {
                        //modify one in the middle
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                          @SuppressWarnings("unchecked") List<Object> asList=(List<Object>)val;
                            asList.set(size/2,new Object());
                            builder.accept(asList);
                        }
                      }
                    }
                }
                return builder.build().toArray();
            }
            
        },
        VsBooleanList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
              Stream.Builder<Object> builder=Stream.builder();
                switch(initParams.collectionType) {
                case BYTE:
                case CHAR:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                  if(size==0) {
                    builder.accept(new PackedBooleanArrSeq.UncheckedList());
                    builder.accept(new PackedBooleanArrSeq.CheckedList());
                    builder.accept(new BooleanArrSeq.UncheckedList());
                    builder.accept(new BooleanArrSeq.CheckedList());
                    builder.accept(new BooleanDblLnkSeq.UncheckedList());
                    builder.accept(new BooleanDblLnkSeq.CheckedList());
                    builder.accept(new PackedBooleanArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new PackedBooleanArrSeq.CheckedList().subList(0,size));
                    builder.accept(new BooleanArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new BooleanArrSeq.CheckedList().subList(0,size));
                    builder.accept(new BooleanDblLnkSeq.UncheckedList().subList(0,size));
                    builder.accept(new BooleanDblLnkSeq.CheckedList().subList(0,size));
                    var uncheckedPackedRoot=new PackedBooleanArrSeq.UncheckedList(10+size);
                    for(int i=0;i<10;++i) {
                        uncheckedPackedRoot.add((i&1)!=0);
                    }
                    builder.accept(uncheckedPackedRoot.subList(5,5+size));
                    builder.accept(new PackedBooleanArrSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                    builder.accept(new BooleanArrSeq.UncheckedList(uncheckedPackedRoot).subList(5,5+size));
                    builder.accept(new BooleanArrSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                    builder.accept(new BooleanDblLnkSeq.UncheckedList(uncheckedPackedRoot).subList(5,5+size));
                    builder.accept(new BooleanDblLnkSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                  }
                  break;
                case BOOLEAN:
                {
                    
                    var thisList=(OmniList.OfBoolean)seq.getCollection();
                    builder.accept(new PackedBooleanArrSeq.UncheckedList(thisList));
                    builder.accept(new PackedBooleanArrSeq.CheckedList(thisList));
                    builder.accept(new BooleanArrSeq.UncheckedList(thisList));
                    builder.accept(new BooleanArrSeq.CheckedList(thisList));
                    builder.accept(new BooleanDblLnkSeq.UncheckedList(thisList));
                    builder.accept(new BooleanDblLnkSeq.CheckedList(thisList));
                    builder.accept(new PackedBooleanArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new PackedBooleanArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new BooleanArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new BooleanArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new BooleanDblLnkSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new BooleanDblLnkSeq.CheckedList(thisList).subList(0,size));
                    var uncheckedPackedRoot=new PackedBooleanArrSeq.UncheckedList(10+size);
                    for(int i=0;i<5;++i) {
                        uncheckedPackedRoot.add((i&1)!=0);
                    }
                    uncheckedPackedRoot.addAll(thisList);
                    for(int i=0;i<5;++i) {
                        uncheckedPackedRoot.add((i&1)!=0);
                    }
                    builder.accept(uncheckedPackedRoot.subList(5,5+size));
                    builder.accept(new PackedBooleanArrSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                    builder.accept(new BooleanArrSeq.UncheckedList(uncheckedPackedRoot).subList(5,5+size));
                    builder.accept(new BooleanArrSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                    builder.accept(new BooleanDblLnkSeq.UncheckedList(uncheckedPackedRoot).subList(5,5+size));
                    builder.accept(new BooleanDblLnkSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                    break;
                }
                case REF:
                    //TODO
                    break;

                default:
                    throw initParams.collectionType.invalid();
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                PackedBooleanArrSeq.UncheckedList uncheckedPackedList;
                switch(initParams.collectionType) {
                case BOOLEAN:
                {
                    Stream.Builder<Object> builder=Stream.builder();
                    //add one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        OmniList.OfBoolean asList=(OmniList.OfBoolean)val;
                        asList.add(false);
                        builder.accept(asList);
                    }
                    if(size>0) {
                        //subtract one
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfBoolean asList=(OmniList.OfBoolean)val;
                            asList.removeBooleanAt(size-1);
                            builder.accept(asList);
                        }
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfBoolean asList=(OmniList.OfBoolean)val;
                            asList.set(size-1,!asList.getBoolean(size-1));
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              OmniList.OfBoolean asList=(OmniList.OfBoolean)val;
                              asList.set(0,!asList.getBoolean(0));
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                                OmniList.OfBoolean asList=(OmniList.OfBoolean)val;
                                asList.set(size/2,!asList.getBoolean(size/2));
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                    return builder.build().toArray();
                }
                case REF:
                    //TODO
                    return OmniArray.OfRef.DEFAULT_ARR;
                case BYTE:{
                    uncheckedPackedList=new PackedBooleanArrSeq.UncheckedList(size);
                    ((OmniList.OfByte)seq.getCollection()).forEach((byte v)->{
                        uncheckedPackedList.add((v&1)!=0);
                    });
                    break;
                }
                case CHAR:{
                    uncheckedPackedList=new PackedBooleanArrSeq.UncheckedList(size);
                    ((OmniList.OfChar)seq.getCollection()).forEach((char v)->{
                        uncheckedPackedList.add((v&1)!=0);
                    });
                    break;
                }
                case SHORT:{
                    uncheckedPackedList=new PackedBooleanArrSeq.UncheckedList(size);
                    ((OmniList.OfShort)seq.getCollection()).forEach((short v)->{
                        uncheckedPackedList.add((v&1)!=0);
                    });
                    break;
                }
                case INT:{
                    uncheckedPackedList=new PackedBooleanArrSeq.UncheckedList(size);
                    ((OmniList.OfInt)seq.getCollection()).forEach((int v)->{
                        uncheckedPackedList.add((v&1)!=0);
                    });
                    break;
                }
                case LONG:{
                    uncheckedPackedList=new PackedBooleanArrSeq.UncheckedList(size);
                    ((OmniList.OfLong)seq.getCollection()).forEach((long v)->{
                        uncheckedPackedList.add((v&1L)!=0);
                    });
                    break;
                }
                case FLOAT:{
                    uncheckedPackedList=new PackedBooleanArrSeq.UncheckedList(size);
                    ((OmniList.OfFloat)seq.getCollection()).forEach((float v)->{
                        final int bits;
                        uncheckedPackedList.add((((bits=Float.floatToRawIntBits(v))^bits>>>16)&1)!=0);
                    });
                    break;
                }
                case DOUBLE:{
                    uncheckedPackedList=new PackedBooleanArrSeq.UncheckedList(size);
                    ((OmniList.OfDouble)seq.getCollection()).forEach((double v)->{
                        final int bits;
                        uncheckedPackedList.add((((bits=Long.hashCode(Double.doubleToRawLongBits(v)))^bits>>>16)&1)!=0);
                    });
                    break;
                }
                default:
                    throw initParams.collectionType.invalid();
                }
                Stream.Builder<Object> builder=Stream.builder();
                if(size!=0) {
                  var checkedPackedList=new PackedBooleanArrSeq.CheckedList(uncheckedPackedList);
                  var uncheckedArrList=new BooleanArrSeq.UncheckedList(uncheckedPackedList);
                  var checkedArrList=new BooleanArrSeq.CheckedList(uncheckedPackedList);
                  var uncheckedDblLnkList=new BooleanDblLnkSeq.UncheckedList(uncheckedPackedList);
                  var checkedDblLnkList=new BooleanDblLnkSeq.CheckedList(uncheckedPackedList);
                  builder.accept(uncheckedPackedList);
                  builder.accept(checkedPackedList);
                  builder.accept(uncheckedArrList);
                  builder.accept(checkedArrList);
                  builder.accept(uncheckedDblLnkList);
                  builder.accept(checkedDblLnkList);
                  builder.accept(uncheckedPackedList.subList(0,size));
                  builder.accept(checkedPackedList.subList(0,size));
                  builder.accept(uncheckedArrList.subList(0,size));
                  builder.accept(checkedArrList.subList(0,size));
                  builder.accept(uncheckedDblLnkList.subList(0,size));
                  builder.accept(checkedDblLnkList.subList(0,size));
                  var uncheckedPackedRoot=new PackedBooleanArrSeq.UncheckedList(10+size);
                  for(int i=0;i<5;++i) {
                      uncheckedPackedRoot.add((i&1)!=0);
                  }
                  uncheckedPackedRoot.addAll(uncheckedPackedList);
                  for(int i=0;i<5;++i) {
                      uncheckedPackedRoot.add((i&1)!=0);
                  }
                  builder.accept(uncheckedPackedRoot.subList(5,5+size));
                  builder.accept(new PackedBooleanArrSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                  builder.accept(new BooleanArrSeq.UncheckedList(uncheckedPackedRoot).subList(5,5+size));
                  builder.accept(new BooleanArrSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                  builder.accept(new BooleanDblLnkSeq.UncheckedList(uncheckedPackedRoot).subList(5,5+size));
                  builder.accept(new BooleanDblLnkSeq.CheckedList(uncheckedPackedRoot).subList(5,5+size));
                }
              
                return builder.build().toArray();
            }
            
        },
        VsByteList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
              Stream.Builder<Object> builder=Stream.builder();
                switch(initParams.collectionType) {
                case BOOLEAN:
                case CHAR:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                  if(size==0) {
                    builder.accept(new ByteArrSeq.UncheckedList());
                    builder.accept(new ByteArrSeq.CheckedList());
                    builder.accept(new ByteDblLnkSeq.UncheckedList());
                    builder.accept(new ByteDblLnkSeq.CheckedList());
                    builder.accept(new ByteArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new ByteArrSeq.CheckedList().subList(0,size));
                    builder.accept(new ByteDblLnkSeq.UncheckedList().subList(0,size));
                    builder.accept(new ByteDblLnkSeq.CheckedList().subList(0,size));
                    var uncheckedRoot=new ByteArrSeq.UncheckedList(10+size);
                    for(int i=0;i<10;++i) {
                      uncheckedRoot.add((byte)i);
                    }  
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new ByteArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new ByteDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new ByteDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  }
                  break;
                case BYTE:
                {
                    
                    var thisList=(OmniList.OfByte)seq.getCollection();
                    builder.accept(new ByteArrSeq.UncheckedList(thisList));
                    builder.accept(new ByteArrSeq.CheckedList(thisList));
                    builder.accept(new ByteDblLnkSeq.UncheckedList(thisList));
                    builder.accept(new ByteDblLnkSeq.CheckedList(thisList));
                    builder.accept(new ByteArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new ByteArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new ByteDblLnkSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new ByteDblLnkSeq.CheckedList(thisList).subList(0,size));
                    var uncheckedRoot=new ByteArrSeq.UncheckedList(10+size);
                    for(int i=0;i<5;++i) {
                        uncheckedRoot.add((byte)i);
                    }
                    uncheckedRoot.addAll(thisList);
                    for(int i=0;i<5;++i) {
                        uncheckedRoot.add((byte)i);
                    }
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new ByteArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new ByteDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new ByteDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    break;
                }
                case REF:
                  //TODO
                  break;
                default:
                    throw initParams.collectionType.invalid();
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                ByteArrSeq.UncheckedList uncheckedArrList;
                switch(initParams.collectionType) {
                case BYTE:
                {
                    Stream.Builder<Object> builder=Stream.builder();
                    //add one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        OmniList.OfByte asList=(OmniList.OfByte)val;
                        asList.add((byte)0);
                        builder.accept(asList);
                    }
                    if(size>0) {
                        //subtract one
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfByte asList=(OmniList.OfByte)val;
                            asList.removeByteAt(size-1);
                            builder.accept(asList);
                        }
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfByte asList=(OmniList.OfByte)val;
                            asList.set(size-1,(byte)(asList.getByte(size-1)+1));
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              OmniList.OfByte asList=(OmniList.OfByte)val;
                              asList.set(0,(byte)(asList.getByte(0)+1));
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                                OmniList.OfByte asList=(OmniList.OfByte)val;
                                asList.set(size/2,(byte)(asList.getByte(size/2)+1));
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                    return builder.build().toArray();
                }
                case REF:
                    //TODO
                    return OmniArray.OfRef.DEFAULT_ARR;
                case BOOLEAN:{
                    uncheckedArrList=new ByteArrSeq.UncheckedList((OmniList.OfBoolean)seq.getCollection());
                    break;
                }
                case CHAR:{
                    uncheckedArrList=new ByteArrSeq.UncheckedList(size);
                    ((OmniList.OfChar)seq.getCollection()).forEach((char v)->{
                        uncheckedArrList.add((byte)v);
                    });
                    break;
                }
                case SHORT:{
                    uncheckedArrList=new ByteArrSeq.UncheckedList(size);
                    ((OmniList.OfShort)seq.getCollection()).forEach((short v)->{
                        uncheckedArrList.add((byte)v);
                    });
                    break;
                }
                case INT:{
                    uncheckedArrList=new ByteArrSeq.UncheckedList(size);
                    ((OmniList.OfInt)seq.getCollection()).forEach((int v)->{
                        uncheckedArrList.add((byte)v);
                    });
                    break;
                }
                case LONG:{
                    uncheckedArrList=new ByteArrSeq.UncheckedList(size);
                    ((OmniList.OfLong)seq.getCollection()).forEach((long v)->{
                        uncheckedArrList.add((byte)v);
                    });
                    break;
                }
                case FLOAT:{
                    uncheckedArrList=new ByteArrSeq.UncheckedList(size);
                    ((OmniList.OfFloat)seq.getCollection()).forEach((float v)->{
                        uncheckedArrList.add((byte)v);
                    });
                    break;
                }
                case DOUBLE:{
                    uncheckedArrList=new ByteArrSeq.UncheckedList(size);
                    ((OmniList.OfDouble)seq.getCollection()).forEach((double v)->{
                        uncheckedArrList.add((byte)v);
                    });
                    break;
                }
                default:
                    throw initParams.collectionType.invalid();
                }
                Stream.Builder<Object> builder=Stream.builder();
                if(size!=0) {
                  var checkedArrList=new ByteArrSeq.CheckedList(uncheckedArrList);
                  var uncheckedDblLnkList=new ByteDblLnkSeq.UncheckedList(uncheckedArrList);
                  var checkedDblLnkList=new ByteDblLnkSeq.CheckedList(uncheckedArrList);
                  builder.accept(uncheckedArrList);
                  builder.accept(checkedArrList);
                  builder.accept(uncheckedDblLnkList);
                  builder.accept(checkedDblLnkList);
                  builder.accept(uncheckedArrList.subList(0,size));
                  builder.accept(checkedArrList.subList(0,size));
                  builder.accept(uncheckedDblLnkList.subList(0,size));
                  builder.accept(checkedDblLnkList.subList(0,size));
                  var uncheckedRoot=new ByteArrSeq.UncheckedList(10+size);
                  for(int i=0;i<5;++i) {
                      uncheckedRoot.add((byte)i);
                  }
                  uncheckedRoot.addAll(uncheckedArrList);
                  for(int i=0;i<5;++i) {
                      uncheckedRoot.add((byte)i);
                  }
                  builder.accept(uncheckedRoot.subList(5,5+size));
                  builder.accept(new ByteArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new ByteDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new ByteDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                }
                return builder.build().toArray();
            }
            
        },
        VsCharList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
              Stream.Builder<Object> builder=Stream.builder();
                switch(initParams.collectionType) {
                case BOOLEAN:
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                  if(size==0) {
                    builder.accept(new CharArrSeq.UncheckedList());
                    builder.accept(new CharArrSeq.CheckedList());
                    builder.accept(new CharDblLnkSeq.UncheckedList());
                    builder.accept(new CharDblLnkSeq.CheckedList());
                    builder.accept(new CharArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new CharArrSeq.CheckedList().subList(0,size));
                    builder.accept(new CharDblLnkSeq.UncheckedList().subList(0,size));
                    builder.accept(new CharDblLnkSeq.CheckedList().subList(0,size));
                    var uncheckedRoot=new CharArrSeq.UncheckedList(10+size);
                    for(int i=0;i<10;++i) {
                      uncheckedRoot.add((char)i);
                    }  
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new CharArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new CharDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new CharDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  }
                  break;
                case CHAR:
                {
                    
                    var thisList=(OmniList.OfChar)seq.getCollection();
                    builder.accept(new CharArrSeq.UncheckedList(thisList));
                    builder.accept(new CharArrSeq.CheckedList(thisList));
                    builder.accept(new CharDblLnkSeq.UncheckedList(thisList));
                    builder.accept(new CharDblLnkSeq.CheckedList(thisList));
                    builder.accept(new CharArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new CharArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new CharDblLnkSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new CharDblLnkSeq.CheckedList(thisList).subList(0,size));
                    var uncheckedRoot=new CharArrSeq.UncheckedList(10+size);
                    for(int i=0;i<5;++i) {
                        uncheckedRoot.add((char)i);
                    }
                    uncheckedRoot.addAll(thisList);
                    for(int i=0;i<5;++i) {
                        uncheckedRoot.add((char)i);
                    }
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new CharArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new CharDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new CharDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    break;
                }
                case REF:
                    //TODO
                    break;
                default:
                    throw initParams.collectionType.invalid();
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                CharArrSeq.UncheckedList uncheckedArrList;
                switch(initParams.collectionType) {
                case CHAR:
                {
                    Stream.Builder<Object> builder=Stream.builder();
                    //add one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        OmniList.OfChar asList=(OmniList.OfChar)val;
                        asList.add((char)0);
                        builder.accept(asList);
                    }
                    if(size>0) {
                        //subtract one
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfChar asList=(OmniList.OfChar)val;
                            asList.removeCharAt(size-1);
                            builder.accept(asList);
                        }
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfChar asList=(OmniList.OfChar)val;
                            asList.set(size-1,(char)(asList.getChar(size-1)+1));
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              OmniList.OfChar asList=(OmniList.OfChar)val;
                              asList.set(0,(char)(asList.getChar(0)+1));
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                                OmniList.OfChar asList=(OmniList.OfChar)val;
                                asList.set(size/2,(char)(asList.getChar(size/2)+1));
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                    return builder.build().toArray();
                }
                case REF:
                    //TODO
                    return OmniArray.OfRef.DEFAULT_ARR;
                case BOOLEAN:{
                    uncheckedArrList=new CharArrSeq.UncheckedList((OmniList.OfBoolean)seq.getCollection());
                    break;
                }
                case BYTE:{
                    uncheckedArrList=new CharArrSeq.UncheckedList(size);
                    ((OmniList.OfByte)seq.getCollection()).forEach((byte v)->{
                        uncheckedArrList.add((char)v);
                    });
                    break;
                }
                case SHORT:{
                    uncheckedArrList=new CharArrSeq.UncheckedList(size);
                    ((OmniList.OfShort)seq.getCollection()).forEach((short v)->{
                        uncheckedArrList.add((char)v);
                    });
                    break;
                }
                case INT:{
                    uncheckedArrList=new CharArrSeq.UncheckedList(size);
                    ((OmniList.OfInt)seq.getCollection()).forEach((int v)->{
                        uncheckedArrList.add((char)v);
                    });
                    break;
                }
                case LONG:{
                    uncheckedArrList=new CharArrSeq.UncheckedList(size);
                    ((OmniList.OfLong)seq.getCollection()).forEach((long v)->{
                        uncheckedArrList.add((char)v);
                    });
                    break;
                }
                case FLOAT:{
                    uncheckedArrList=new CharArrSeq.UncheckedList(size);
                    ((OmniList.OfFloat)seq.getCollection()).forEach((float v)->{
                        uncheckedArrList.add((char)v);
                    });
                    break;
                }
                case DOUBLE:{
                    uncheckedArrList=new CharArrSeq.UncheckedList(size);
                    ((OmniList.OfDouble)seq.getCollection()).forEach((double v)->{
                        uncheckedArrList.add((char)v);
                    });
                    break;
                }
                default:
                    throw initParams.collectionType.invalid();
                }
                Stream.Builder<Object> builder=Stream.builder();
                if(size!=0) {
                  var checkedArrList=new CharArrSeq.CheckedList(uncheckedArrList);
                  var uncheckedDblLnkList=new CharDblLnkSeq.UncheckedList(uncheckedArrList);
                  var checkedDblLnkList=new CharDblLnkSeq.CheckedList(uncheckedArrList);
                  builder.accept(uncheckedArrList);
                  builder.accept(checkedArrList);
                  builder.accept(uncheckedDblLnkList);
                  builder.accept(checkedDblLnkList);
                  builder.accept(uncheckedArrList.subList(0,size));
                  builder.accept(checkedArrList.subList(0,size));
                  builder.accept(uncheckedDblLnkList.subList(0,size));
                  builder.accept(checkedDblLnkList.subList(0,size));
                  var uncheckedRoot=new CharArrSeq.UncheckedList(10+size);
                  for(int i=0;i<5;++i) {
                      uncheckedRoot.add((char)i);
                  }
                  uncheckedRoot.addAll(uncheckedArrList);
                  for(int i=0;i<5;++i) {
                      uncheckedRoot.add((char)i);
                  }
                  builder.accept(uncheckedRoot.subList(5,5+size));
                  builder.accept(new CharArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new CharDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new CharDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                }
               
                return builder.build().toArray();
            }
            
        },
        VsShortList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
              Stream.Builder<Object> builder=Stream.builder();
                switch(initParams.collectionType) {
                case BOOLEAN:
                case CHAR:
                case BYTE:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                  if(size==0) {
                    builder.accept(new ShortArrSeq.UncheckedList());
                    builder.accept(new ShortArrSeq.CheckedList());
                    builder.accept(new ShortDblLnkSeq.UncheckedList());
                    builder.accept(new ShortDblLnkSeq.CheckedList());
                    builder.accept(new ShortArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new ShortArrSeq.CheckedList().subList(0,size));
                    builder.accept(new ShortDblLnkSeq.UncheckedList().subList(0,size));
                    builder.accept(new ShortDblLnkSeq.CheckedList().subList(0,size));
                    var uncheckedRoot=new ShortArrSeq.UncheckedList(10+size);
                    for(int i=0;i<10;++i) {
                      uncheckedRoot.add((short)i);
                    }  
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new ShortArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new ShortDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new ShortDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  }
                  break;
                case SHORT:
                {
                    
                    var thisList=(OmniList.OfShort)seq.getCollection();
                    builder.accept(new ShortArrSeq.UncheckedList(thisList));
                    builder.accept(new ShortArrSeq.CheckedList(thisList));
                    builder.accept(new ShortDblLnkSeq.UncheckedList(thisList));
                    builder.accept(new ShortDblLnkSeq.CheckedList(thisList));
                    builder.accept(new ShortArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new ShortArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new ShortDblLnkSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new ShortDblLnkSeq.CheckedList(thisList).subList(0,size));
                    var uncheckedRoot=new ShortArrSeq.UncheckedList(10+size);
                    for(int i=0;i<5;++i) {
                        uncheckedRoot.add((short)i);
                    }
                    uncheckedRoot.addAll(thisList);
                    for(int i=0;i<5;++i) {
                        uncheckedRoot.add((short)i);
                    }
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new ShortArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new ShortDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new ShortDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    break;
                }
                case REF:
                    //TODO
                    break;
                default:
                    throw initParams.collectionType.invalid();
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                ShortArrSeq.UncheckedList uncheckedArrList;
                switch(initParams.collectionType) {
                case SHORT:
                {
                    Stream.Builder<Object> builder=Stream.builder();
                    //add one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        OmniList.OfShort asList=(OmniList.OfShort)val;
                        asList.add((short)0);
                        builder.accept(asList);
                    }
                    if(size>0) {
                        //subtract one
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfShort asList=(OmniList.OfShort)val;
                            asList.removeShortAt(size-1);
                            builder.accept(asList);
                        }
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfShort asList=(OmniList.OfShort)val;
                            asList.set(size-1,(short)(asList.getShort(size-1)+1));
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              OmniList.OfShort asList=(OmniList.OfShort)val;
                              asList.set(0,(short)(asList.getShort(0)+1));
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                                OmniList.OfShort asList=(OmniList.OfShort)val;
                                asList.set(size/2,(short)(asList.getShort(size/2)+1));
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                    return builder.build().toArray();
                }
                case REF:
                    //TODO
                    return OmniArray.OfRef.DEFAULT_ARR;
                case BOOLEAN:
                case BYTE:{
                    uncheckedArrList=new ShortArrSeq.UncheckedList((OmniList.ShortOutput<?>)seq.getCollection());
                    break;
                }
                case CHAR:{
                    uncheckedArrList=new ShortArrSeq.UncheckedList(size);
                    ((OmniList.OfChar)seq.getCollection()).forEach((char v)->{
                        uncheckedArrList.add((short)v);
                    });
                    break;
                }
               
                case INT:{
                    uncheckedArrList=new ShortArrSeq.UncheckedList(size);
                    ((OmniList.OfInt)seq.getCollection()).forEach((int v)->{
                        uncheckedArrList.add((short)v);
                    });
                    break;
                }
                case LONG:{
                    uncheckedArrList=new ShortArrSeq.UncheckedList(size);
                    ((OmniList.OfLong)seq.getCollection()).forEach((long v)->{
                        uncheckedArrList.add((short)v);
                    });
                    break;
                }
                case FLOAT:{
                    uncheckedArrList=new ShortArrSeq.UncheckedList(size);
                    ((OmniList.OfFloat)seq.getCollection()).forEach((float v)->{
                        uncheckedArrList.add((short)v);
                    });
                    break;
                }
                case DOUBLE:{
                    uncheckedArrList=new ShortArrSeq.UncheckedList(size);
                    ((OmniList.OfDouble)seq.getCollection()).forEach((double v)->{
                        uncheckedArrList.add((short)v);
                    });
                    break;
                }
                default:
                    throw initParams.collectionType.invalid();
                }
                Stream.Builder<Object> builder=Stream.builder();
                if(size!=0) {
                  var checkedArrList=new ShortArrSeq.CheckedList(uncheckedArrList);
                  var uncheckedDblLnkList=new ShortDblLnkSeq.UncheckedList(uncheckedArrList);
                  var checkedDblLnkList=new ShortDblLnkSeq.CheckedList(uncheckedArrList);
                  builder.accept(uncheckedArrList);
                  builder.accept(checkedArrList);
                  builder.accept(uncheckedDblLnkList);
                  builder.accept(checkedDblLnkList);
                  builder.accept(uncheckedArrList.subList(0,size));
                  builder.accept(checkedArrList.subList(0,size));
                  builder.accept(uncheckedDblLnkList.subList(0,size));
                  builder.accept(checkedDblLnkList.subList(0,size));
                  var uncheckedRoot=new ShortArrSeq.UncheckedList(10+size);
                  for(int i=0;i<5;++i) {
                      uncheckedRoot.add((short)i);
                  }
                  uncheckedRoot.addAll(uncheckedArrList);
                  for(int i=0;i<5;++i) {
                      uncheckedRoot.add((short)i);
                  }
                  builder.accept(uncheckedRoot.subList(5,5+size));
                  builder.accept(new ShortArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new ShortDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new ShortDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                }
                
                return builder.build().toArray();
            }
            
        },
        VsIntList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
              Stream.Builder<Object> builder=Stream.builder();
                switch(initParams.collectionType) {
                case BOOLEAN:
                case CHAR:
                case BYTE:
                case SHORT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                  if(size==0) {
                    builder.accept(new IntArrSeq.UncheckedList());
                    builder.accept(new IntArrSeq.CheckedList());
                    builder.accept(new IntDblLnkSeq.UncheckedList());
                    builder.accept(new IntDblLnkSeq.CheckedList());
                    builder.accept(new IntArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new IntArrSeq.CheckedList().subList(0,size));
                    builder.accept(new IntDblLnkSeq.UncheckedList().subList(0,size));
                    builder.accept(new IntDblLnkSeq.CheckedList().subList(0,size));
                    var uncheckedRoot=new IntArrSeq.UncheckedList(10+size);
                    for(int i=0;i<10;++i) {
                      uncheckedRoot.add(i);
                    }  
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new IntArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new IntDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new IntDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  }
                  break;
                case INT:
                {
                   
                    var thisList=(OmniList.OfInt)seq.getCollection();
                    builder.accept(new IntArrSeq.UncheckedList(thisList));
                    builder.accept(new IntArrSeq.CheckedList(thisList));
                    builder.accept(new IntDblLnkSeq.UncheckedList(thisList));
                    builder.accept(new IntDblLnkSeq.CheckedList(thisList));
                    builder.accept(new IntArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new IntArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new IntDblLnkSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new IntDblLnkSeq.CheckedList(thisList).subList(0,size));
                    var uncheckedRoot=new IntArrSeq.UncheckedList(10+size);
                    for(int i=0;i<5;++i) {
                        uncheckedRoot.add(i);
                    }
                    uncheckedRoot.addAll(thisList);
                    for(int i=0;i<5;++i) {
                        uncheckedRoot.add(i);
                    }
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new IntArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new IntDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new IntDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    break;
                }
                case REF:
                    //TODO
                    break;
                default:
                    throw initParams.collectionType.invalid();
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                IntArrSeq.UncheckedList uncheckedArrList;
                switch(initParams.collectionType) {
                case INT:
                {
                    Stream.Builder<Object> builder=Stream.builder();
                    //add one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        OmniList.OfInt asList=(OmniList.OfInt)val;
                        asList.add(0);
                        builder.accept(asList);
                    }
                    if(size>0) {
                        //subtract one
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfInt asList=(OmniList.OfInt)val;
                            asList.removeIntAt(size-1);
                            builder.accept(asList);
                        }
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfInt asList=(OmniList.OfInt)val;
                            asList.set(size-1,asList.getInt(size-1)+1);
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              OmniList.OfInt asList=(OmniList.OfInt)val;
                              asList.set(0,asList.getInt(0)+1);
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                                OmniList.OfInt asList=(OmniList.OfInt)val;
                                asList.set(size/2,asList.getInt(size/2)+1);
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                    return builder.build().toArray();
                }
                case REF:
                    //TODO
                    return OmniArray.OfRef.DEFAULT_ARR;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case SHORT:{
                    uncheckedArrList=new IntArrSeq.UncheckedList((OmniList.IntOutput<?>)seq.getCollection());
                    break;
                }
                case LONG:{
                    uncheckedArrList=new IntArrSeq.UncheckedList(size);
                    ((OmniList.OfLong)seq.getCollection()).forEach((long v)->{
                        uncheckedArrList.add((int)v);
                    });
                    break;
                }
                case FLOAT:{
                    uncheckedArrList=new IntArrSeq.UncheckedList(size);
                    ((OmniList.OfFloat)seq.getCollection()).forEach((float v)->{
                        uncheckedArrList.add((int)v);
                    });
                    break;
                }
                case DOUBLE:{
                    uncheckedArrList=new IntArrSeq.UncheckedList(size);
                    ((OmniList.OfDouble)seq.getCollection()).forEach((double v)->{
                        uncheckedArrList.add((int)v);
                    });
                    break;
                }
                default:
                    throw initParams.collectionType.invalid();
                }
                Stream.Builder<Object> builder=Stream.builder();
                if(size!=0) {
                  var checkedArrList=new IntArrSeq.CheckedList(uncheckedArrList);
                  var uncheckedDblLnkList=new IntDblLnkSeq.UncheckedList(uncheckedArrList);
                  var checkedDblLnkList=new IntDblLnkSeq.CheckedList(uncheckedArrList);
                  builder.accept(uncheckedArrList);
                  builder.accept(checkedArrList);
                  builder.accept(uncheckedDblLnkList);
                  builder.accept(checkedDblLnkList);
                  builder.accept(uncheckedArrList.subList(0,size));
                  builder.accept(checkedArrList.subList(0,size));
                  builder.accept(uncheckedDblLnkList.subList(0,size));
                  builder.accept(checkedDblLnkList.subList(0,size));
                  var uncheckedRoot=new IntArrSeq.UncheckedList(10+size);
                  for(int i=0;i<5;++i) {
                      uncheckedRoot.add(i);
                  }
                  uncheckedRoot.addAll(uncheckedArrList);
                  for(int i=0;i<5;++i) {
                      uncheckedRoot.add(i);
                  }
                  builder.accept(uncheckedRoot.subList(5,5+size));
                  builder.accept(new IntArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new IntDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new IntDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                }
                
                return builder.build().toArray();
            }
            
        },
        VsLongList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
              Stream.Builder<Object> builder=Stream.builder();
                switch(initParams.collectionType) {
                case BOOLEAN:
                case CHAR:
                case BYTE:
                case SHORT:
                case INT:
                case FLOAT:
                case DOUBLE:
                  if(size==0) {
                    builder.accept(new LongArrSeq.UncheckedList());
                    builder.accept(new LongArrSeq.CheckedList());
                    builder.accept(new LongDblLnkSeq.UncheckedList());
                    builder.accept(new LongDblLnkSeq.CheckedList());
                    builder.accept(new LongArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new LongArrSeq.CheckedList().subList(0,size));
                    builder.accept(new LongDblLnkSeq.UncheckedList().subList(0,size));
                    builder.accept(new LongDblLnkSeq.CheckedList().subList(0,size));
                    var uncheckedRoot=new LongArrSeq.UncheckedList(10+size);
                    for(long i=0;i<10;++i) {
                      uncheckedRoot.add(i);
                    }   
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new LongArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new LongDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new LongDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  }
                  break;
                case LONG:
                {
                    
                    var thisList=(OmniList.OfLong)seq.getCollection();
                    builder.accept(new LongArrSeq.UncheckedList(thisList));
                    builder.accept(new LongArrSeq.CheckedList(thisList));
                    builder.accept(new LongDblLnkSeq.UncheckedList(thisList));
                    builder.accept(new LongDblLnkSeq.CheckedList(thisList));
                    builder.accept(new LongArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new LongArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new LongDblLnkSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new LongDblLnkSeq.CheckedList(thisList).subList(0,size));
                    var uncheckedRoot=new LongArrSeq.UncheckedList(10+size);
                    for(long i=0;i<5;++i) {
                        uncheckedRoot.add(i);
                    }
                    uncheckedRoot.addAll(thisList);
                    for(long i=0;i<5;++i) {
                        uncheckedRoot.add(i);
                    }
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new LongArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new LongDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new LongDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    break;
                }
                case REF:
                    //TODO
                    break;
                default:
                    throw initParams.collectionType.invalid();
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                LongArrSeq.UncheckedList uncheckedArrList;
                switch(initParams.collectionType) {
                case LONG:
                {
                    Stream.Builder<Object> builder=Stream.builder();
                    //add one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        OmniList.OfLong asList=(OmniList.OfLong)val;
                        asList.add(0L);
                        builder.accept(asList);
                    }
                    if(size>0) {
                        //subtract one
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfLong asList=(OmniList.OfLong)val;
                            asList.removeLongAt(size-1);
                            builder.accept(asList);
                        }
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfLong asList=(OmniList.OfLong)val;
                            asList.set(size-1,asList.getLong(size-1)+1);
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              OmniList.OfLong asList=(OmniList.OfLong)val;
                              asList.set(0,asList.getLong(0)+1);
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                                OmniList.OfLong asList=(OmniList.OfLong)val;
                                asList.set(size/2,asList.getLong(size/2)+1);
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                    return builder.build().toArray();
                }
                case REF:
                    //TODO
                    return OmniArray.OfRef.DEFAULT_ARR;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case SHORT:
                case INT:{
                    uncheckedArrList=new LongArrSeq.UncheckedList((OmniList.LongOutput<?>)seq.getCollection());
                    break;
                }
                case FLOAT:{
                    uncheckedArrList=new LongArrSeq.UncheckedList(size);
                    ((OmniList.OfFloat)seq.getCollection()).forEach((float v)->{
                        uncheckedArrList.add((long)v);
                    });
                    break;
                }
                case DOUBLE:{
                    uncheckedArrList=new LongArrSeq.UncheckedList(size);
                    ((OmniList.OfDouble)seq.getCollection()).forEach((double v)->{
                        uncheckedArrList.add((long)v);
                    });
                    break;
                }
                default:
                    throw initParams.collectionType.invalid();
                }
                Stream.Builder<Object> builder=Stream.builder();
                if(size!=0) {
                  var checkedArrList=new LongArrSeq.CheckedList(uncheckedArrList);
                  var uncheckedDblLnkList=new LongDblLnkSeq.UncheckedList(uncheckedArrList);
                  var checkedDblLnkList=new LongDblLnkSeq.CheckedList(uncheckedArrList);
                  builder.accept(uncheckedArrList);
                  builder.accept(checkedArrList);
                  builder.accept(uncheckedDblLnkList);
                  builder.accept(checkedDblLnkList);
                  builder.accept(uncheckedArrList.subList(0,size));
                  builder.accept(checkedArrList.subList(0,size));
                  builder.accept(uncheckedDblLnkList.subList(0,size));
                  builder.accept(checkedDblLnkList.subList(0,size));
                  var uncheckedRoot=new LongArrSeq.UncheckedList(10+size);
                  for(long i=0;i<5;++i) {
                      uncheckedRoot.add(i);
                  }
                  uncheckedRoot.addAll(uncheckedArrList);
                  for(long i=0;i<5;++i) {
                      uncheckedRoot.add(i);
                  }
                  builder.accept(uncheckedRoot.subList(5,5+size));
                  builder.accept(new LongArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new LongDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new LongDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                }
               
                return builder.build().toArray();
            }
            
        },
        VsFloatList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
              Stream.Builder<Object> builder=Stream.builder();
                switch(initParams.collectionType) {
                case BOOLEAN:
                case CHAR:
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case DOUBLE:
                  if(size==0) {
                    builder.accept(new FloatArrSeq.UncheckedList());
                    builder.accept(new FloatArrSeq.CheckedList());
                    builder.accept(new FloatDblLnkSeq.UncheckedList());
                    builder.accept(new FloatDblLnkSeq.CheckedList());
                    builder.accept(new FloatArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new FloatArrSeq.CheckedList().subList(0,size));
                    builder.accept(new FloatDblLnkSeq.UncheckedList().subList(0,size));
                    builder.accept(new FloatDblLnkSeq.CheckedList().subList(0,size));
                    var uncheckedRoot=new FloatArrSeq.UncheckedList(10+size);
                    for(float i=0;i<10;++i) {
                      uncheckedRoot.add(i);
                    }   
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new FloatArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new FloatDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new FloatDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  }
                  break;
                case FLOAT:
                {
                   
                    var thisList=(OmniList.OfFloat)seq.getCollection();
                    builder.accept(new FloatArrSeq.UncheckedList(thisList));
                    builder.accept(new FloatArrSeq.CheckedList(thisList));
                    builder.accept(new FloatDblLnkSeq.UncheckedList(thisList));
                    builder.accept(new FloatDblLnkSeq.CheckedList(thisList));
                    builder.accept(new FloatArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new FloatArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new FloatDblLnkSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new FloatDblLnkSeq.CheckedList(thisList).subList(0,size));
                    var uncheckedRoot=new FloatArrSeq.UncheckedList(10+size);
                    for(float i=0;i<5;++i) {
                        uncheckedRoot.add(i);
                    }
                    uncheckedRoot.addAll(thisList);
                    for(float i=0;i<5;++i) {
                        uncheckedRoot.add(i);
                    }
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new FloatArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new FloatDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new FloatDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    break;
                }
                case REF:
                    //TODO
                    break;
               
                default:
                    throw initParams.collectionType.invalid();
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                FloatArrSeq.UncheckedList uncheckedArrList;
                switch(initParams.collectionType) {
                case FLOAT:
                {
                    Stream.Builder<Object> builder=Stream.builder();
                    //add one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        OmniList.OfFloat asList=(OmniList.OfFloat)val;
                        asList.add(0F);
                        builder.accept(asList);
                    }
                    if(size>0) {
                        //subtract one
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfFloat asList=(OmniList.OfFloat)val;
                            asList.removeFloatAt(size-1);
                            builder.accept(asList);
                        }
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfFloat asList=(OmniList.OfFloat)val;
                            asList.set(size-1,asList.getFloat(size-1)+1);
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              OmniList.OfFloat asList=(OmniList.OfFloat)val;
                              asList.set(0,asList.getFloat(0)+1);
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                                OmniList.OfFloat asList=(OmniList.OfFloat)val;
                                asList.set(size/2,asList.getFloat(size/2)+1);
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                    return builder.build().toArray();
                }
                case REF:
                    //TODO
                    return OmniArray.OfRef.DEFAULT_ARR;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case SHORT:
                case INT:
                case LONG:{
                    uncheckedArrList=new FloatArrSeq.UncheckedList((OmniList.FloatOutput<?>)seq.getCollection());
                    break;
                }
                case DOUBLE:{
                    uncheckedArrList=new FloatArrSeq.UncheckedList(size);
                    ((OmniList.OfDouble)seq.getCollection()).forEach((double v)->{
                        uncheckedArrList.add((float)v);
                    });
                    break;
                }
                default:
                    throw initParams.collectionType.invalid();
                }
                Stream.Builder<Object> builder=Stream.builder();
                if(size!=0) {
                  var checkedArrList=new FloatArrSeq.CheckedList(uncheckedArrList);
                  var uncheckedDblLnkList=new FloatDblLnkSeq.UncheckedList(uncheckedArrList);
                  var checkedDblLnkList=new FloatDblLnkSeq.CheckedList(uncheckedArrList);
                  builder.accept(uncheckedArrList);
                  builder.accept(checkedArrList);
                  builder.accept(uncheckedDblLnkList);
                  builder.accept(checkedDblLnkList);
                  builder.accept(uncheckedArrList.subList(0,size));
                  builder.accept(checkedArrList.subList(0,size));
                  builder.accept(uncheckedDblLnkList.subList(0,size));
                  builder.accept(checkedDblLnkList.subList(0,size));
                  var uncheckedRoot=new FloatArrSeq.UncheckedList(10+size);
                  for(float i=0;i<5;++i) {
                      uncheckedRoot.add(i);
                  }
                  uncheckedRoot.addAll(uncheckedArrList);
                  for(float i=0;i<5;++i) {
                      uncheckedRoot.add(i);
                  }
                  builder.accept(uncheckedRoot.subList(5,5+size));
                  builder.accept(new FloatArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new FloatDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new FloatDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                }
              
                return builder.build().toArray();
            }
            
        },
        VsDoubleList{

            @Override
            Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
              Stream.Builder<Object> builder=Stream.builder();
                switch(initParams.collectionType) {
                case BOOLEAN:
                case CHAR:
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                  if(size==0) {
                    builder.accept(new DoubleArrSeq.UncheckedList());
                    builder.accept(new DoubleArrSeq.CheckedList());
                    builder.accept(new DoubleDblLnkSeq.UncheckedList());
                    builder.accept(new DoubleDblLnkSeq.CheckedList());
                    builder.accept(new DoubleArrSeq.UncheckedList().subList(0,size));
                    builder.accept(new DoubleArrSeq.CheckedList().subList(0,size));
                    builder.accept(new DoubleDblLnkSeq.UncheckedList().subList(0,size));
                    builder.accept(new DoubleDblLnkSeq.CheckedList().subList(0,size));
                    var uncheckedRoot=new DoubleArrSeq.UncheckedList(10+size);
                    for(double i=0;i<10;++i) {
                        uncheckedRoot.add(i);
                    }     
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new DoubleArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new DoubleDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new DoubleDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  }
                  break;
                case DOUBLE:
                {
                   
                    var thisList=(OmniList.OfDouble)seq.getCollection();
                    builder.accept(new DoubleArrSeq.UncheckedList(thisList));
                    builder.accept(new DoubleArrSeq.CheckedList(thisList));
                    builder.accept(new DoubleDblLnkSeq.UncheckedList(thisList));
                    builder.accept(new DoubleDblLnkSeq.CheckedList(thisList));
                    builder.accept(new DoubleArrSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new DoubleArrSeq.CheckedList(thisList).subList(0,size));
                    builder.accept(new DoubleDblLnkSeq.UncheckedList(thisList).subList(0,size));
                    builder.accept(new DoubleDblLnkSeq.CheckedList(thisList).subList(0,size));
                    var uncheckedRoot=new DoubleArrSeq.UncheckedList(10+size);
                    for(double i=0;i<5;++i) {
                        uncheckedRoot.add(i);
                    }
                    uncheckedRoot.addAll(thisList);
                    for(double i=0;i<5;++i) {
                        uncheckedRoot.add(i);
                    }
                    builder.accept(uncheckedRoot.subList(5,5+size));
                    builder.accept(new DoubleArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new DoubleDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                    builder.accept(new DoubleDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                    break;
                }
                case REF:
                    //TODO
                    break;
                default:
                    throw initParams.collectionType.invalid();
                }
                return builder.build().toArray();
            }

            @Override
            Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,
                    SequenceInitialization initSeq,int size,int period,int initVal){
                DoubleArrSeq.UncheckedList uncheckedArrList;
                switch(initParams.collectionType) {
                case DOUBLE:
                {
                    Stream.Builder<Object> builder=Stream.builder();
                    //add one
                    for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                        OmniList.OfDouble asList=(OmniList.OfDouble)val;
                        asList.add(0D);
                        builder.accept(asList);
                    }
                    if(size>0) {
                        //subtract one
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfDouble asList=(OmniList.OfDouble)val;
                            asList.removeDoubleAt(size-1);
                            builder.accept(asList);
                        }
                        //modify one at the end
                        for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                            OmniList.OfDouble asList=(OmniList.OfDouble)val;
                            asList.set(size-1,asList.getDouble(size-1)+1);
                            builder.accept(asList);
                        }
                        if(size>1) {
                          //modify one at the beginning
                          for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                              OmniList.OfDouble asList=(OmniList.OfDouble)val;
                              asList.set(0,asList.getDouble(0)+1);
                              builder.accept(asList);
                          }
                          if(size>2) {
                            //modify one in the middle
                            for(Object val:getEqualsOpponents(seq,initParams,initSeq,size,period,initVal)) {
                                OmniList.OfDouble asList=(OmniList.OfDouble)val;
                                asList.set(size/2,asList.getDouble(size/2)+1);
                                builder.accept(asList);
                            }
                          }
                        }
                    }
                    return builder.build().toArray();
                }
                case REF:
                    //TODO
                    return OmniArray.OfRef.DEFAULT_ARR;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:{
                    uncheckedArrList=new DoubleArrSeq.UncheckedList((OmniList.DoubleOutput<?>)seq.getCollection());
                    break;
                }
                default:
                    throw initParams.collectionType.invalid();
                }
                Stream.Builder<Object> builder=Stream.builder();
                if(size!=0) {
                  var checkedArrList=new DoubleArrSeq.CheckedList(uncheckedArrList);
                  var uncheckedDblLnkList=new DoubleDblLnkSeq.UncheckedList(uncheckedArrList);
                  var checkedDblLnkList=new DoubleDblLnkSeq.CheckedList(uncheckedArrList);
                  builder.accept(uncheckedArrList);
                  builder.accept(checkedArrList);
                  builder.accept(uncheckedDblLnkList);
                  builder.accept(checkedDblLnkList);
                  builder.accept(uncheckedArrList.subList(0,size));
                  builder.accept(checkedArrList.subList(0,size));
                  builder.accept(uncheckedDblLnkList.subList(0,size));
                  builder.accept(checkedDblLnkList.subList(0,size));
                  var uncheckedRoot=new DoubleArrSeq.UncheckedList(10+size);
                  for(double i=0;i<5;++i) {
                      uncheckedRoot.add(i);
                  }
                  uncheckedRoot.addAll(uncheckedArrList);
                  for(double i=0;i<5;++i) {
                      uncheckedRoot.add(i);
                  }
                  builder.accept(uncheckedRoot.subList(5,5+size));
                  builder.accept(new DoubleArrSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new DoubleDblLnkSeq.UncheckedList(uncheckedRoot).subList(5,5+size));
                  builder.accept(new DoubleDblLnkSeq.CheckedList(uncheckedRoot).subList(5,5+size));
                }
              
                return builder.build().toArray();
            }
            
        },
        ;
        
        abstract Object[] getEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,SequenceInitialization initSeq,int size,int period,int initVal);
        abstract Object[] getNotEqualsOpponents(MonitoredList<OmniList<?>> seq,SequenceInitParams initParams,SequenceInitialization initSeq,int size,int period,int initVal);
        
    }
    
    
    @SuppressWarnings("unchecked")
    private static MonitoredList<OmniList<?>> getMonitoredList(SequenceInitParams initParams,int initCapacity){
        var structType=initParams.structType;
        switch(structType) {
        case ArrList:
        case ArrSubList:
            return (MonitoredList<OmniList<?>>)ArrSeqTest.getMonitoredList(initParams,initCapacity);
        case DblLnkList:
        case DblLnkSubList:
            return (MonitoredList<OmniList<?>>)DblLnkSeqTest.getMonitoredList(initParams,initCapacity);
        case PackedBooleanArrList:
        case PackedBooleanArrSubList:
            return (MonitoredList<OmniList<?>>)PackedBooleanArrSeqTest.getMonitoredList(initParams,initCapacity);
        default:
            throw structType.invalid();
        }
    } 

    static Object[] getRefModEqualsOpponents(List<?> list,MonitoredObjectGen objGen,MonitoredList<?> monitoredList,MonitoredObjectGen.ThrowSwitch throwSwitch) {
        throwSwitch.doThrow=false;
        
        Stream.Builder<Object> builder=Stream.builder();
        ArrayList<Object> arrList=new ArrayList<>();
        int size=list.size();
        for(int i=0;i<size;++i) {
            arrList.add(objGen.getMonitoredObject(monitoredList,throwSwitch));
        }
        builder.add(arrList);
        builder.add(new RefArrSeq.CheckedList<>(arrList));
        builder.add(new RefArrSeq.CheckedList<>(arrList).subList(0,size));
        builder.add(new RefDblLnkSeq.CheckedList<>(arrList));
        builder.add(new RefDblLnkSeq.CheckedList<>(arrList).subList(0,size));
        builder.add(new RefArrSeq.UncheckedList<>(arrList));
        builder.add(new RefArrSeq.UncheckedList<>(arrList).subList(0,size));
        builder.add(new RefDblLnkSeq.UncheckedList<>(arrList));
        builder.add(new RefDblLnkSeq.UncheckedList<>(arrList).subList(0,size));
        throwSwitch.doThrow=true;
        return builder.build().toArray();
    }
    
    
    @Test
    public void testequals_Object() {
        for(var initParams:LIST_INIT_PARAMS) {
            final int[] sizes;
            switch(initParams.structType){
                case PackedBooleanArrList:
                case PackedBooleanArrSubList:
                    sizes=PACKED_BOOLEAN_SIZES;
                    break;
                default:
                    sizes=SIZES;
                    break;
            }
            for(var size:sizes) {
                for(var illegalMod:initParams.structType.validPreMods) {
                    if(illegalMod!=IllegalModification.ModParent && (initParams.checkedType.checked || illegalMod.expectedException==null)) {
                        if(illegalMod.expectedException==null) {
                            if(size>0 && initParams.checkedType.checked && initParams.collectionType==DataType.REF) {
                                for(var objGen:initParams.structType.validMonitoredObjectGens) {
                                    if(objGen.minDepth<2 && objGen.expectedException!=null) {
                                        TestExecutorService.submitTest(()->{
                                            MonitoredObjectGen.ThrowSwitch throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                                            var thisListMonitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(getMonitoredList(initParams,size),size,0,objGen,throwSwitch);
                                            var thisList=thisListMonitor.getCollection();
                                            for(var equalsOpponent:getRefModEqualsOpponents(thisList,objGen,thisListMonitor,throwSwitch)) {
                                                Assertions.assertThrows(objGen.expectedException,()->thisList.equals(equalsOpponent));
                                                throwSwitch.doThrow=false;
                                                thisListMonitor.verifyCollectionState();
                                                thisListMonitor.repairModCount();
                                                throwSwitch.doThrow=true;
                                            }
                                            thisListMonitor.verifyCollectionState();
                                        });
                                    }
                                }
                            }
                            TestExecutorService.submitTest(()->{
                                var thisListMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
                                var thisList=thisListMonitor.getCollection();
                                for(var equalsOpponentType:EqualsOpponentTypes.values()) {
                                    for(var equalsOpponent:equalsOpponentType.getEqualsOpponents(thisListMonitor,initParams,SequenceInitialization.Ascending,size,0,0)) {
                                        Assertions.assertTrue(thisList.equals(equalsOpponent));
                                        Assertions.assertTrue(equalsOpponent.equals(thisList));
                                    }
                                    for(var notEqualsOpponent:equalsOpponentType.getNotEqualsOpponents(thisListMonitor,initParams,SequenceInitialization.Ascending,size,0,0)) {
                                        Assertions.assertFalse(thisList.equals(notEqualsOpponent));    
                                        if(notEqualsOpponent!=null) {
                                            Assertions.assertFalse(notEqualsOpponent.equals(thisList));
                                            
                                        }
                                    }
                                }
                                thisListMonitor.verifyCollectionState();
                            });
                        }else if(initParams.checkedType.checked) {
                            if(size>0 && initParams.collectionType==DataType.REF) {
                                for(var objGen:initParams.structType.validMonitoredObjectGens) {
                                    if(objGen.minDepth<2 && objGen.expectedException!=null) {
                                        TestExecutorService.submitTest(()->{
                                            MonitoredObjectGen.ThrowSwitch throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                                            var thisListMonitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(getMonitoredList(initParams,size),size,0,objGen,throwSwitch);
                                            var thisList=thisListMonitor.getCollection();
                                            for(var equalsOpponent:getRefModEqualsOpponents(thisList,objGen,thisListMonitor,throwSwitch)) {
                                                thisListMonitor.illegalMod(illegalMod);
                                                Assertions.assertThrows(illegalMod.expectedException,()->thisList.equals(equalsOpponent));
                                                
                                                throwSwitch.doThrow=false;
                                                thisListMonitor.verifyCollectionState();
                                                thisListMonitor.repairModCount();
                                                throwSwitch.doThrow=true;
                                                thisListMonitor.illegalMod(illegalMod);
                                                Assertions.assertThrows(illegalMod.expectedException,()->equalsOpponent.equals(thisList));
                                                
                                                throwSwitch.doThrow=false;
                                                thisListMonitor.verifyCollectionState();
                                                thisListMonitor.repairModCount();
                                                throwSwitch.doThrow=true;
                                            }
                                        });
                                    }
                                }
                            }
                            TestExecutorService.submitTest(()->{
                                var thisListMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
                                var thisList=thisListMonitor.getCollection();
                                for(var equalsOpponentType:EqualsOpponentTypes.values()) {
                                    for(var equalsOpponent:equalsOpponentType.getEqualsOpponents(thisListMonitor,initParams,SequenceInitialization.Ascending,size,0,0)) {
                                        thisListMonitor.illegalMod(illegalMod);
                                        if(equalsOpponent==thisList) {
                                            Assertions.assertTrue(thisList.equals(equalsOpponent));
                                            Assertions.assertTrue(equalsOpponent.equals(thisList));
                                        }else {
                                            Assertions.assertThrows(illegalMod.expectedException,()->thisList.equals(equalsOpponent));
                                            Assertions.assertThrows(illegalMod.expectedException,()->equalsOpponent.equals(thisList));
                                        }
                                        thisListMonitor.verifyCollectionState();
                                        thisListMonitor.repairModCount();
                                    }
                                    outer:for(var notEqualsOpponent:equalsOpponentType.getNotEqualsOpponents(thisListMonitor,initParams,SequenceInitialization.Ascending,size,0,0)) {
                                        thisListMonitor.illegalMod(illegalMod);
                                        goToNoThrow:for(;;) {
                                            goToThrowOneDirection:for(;;) {
                                                if(!(notEqualsOpponent instanceof List)) {
                                                    break goToNoThrow;
                                                }
                                                if(notEqualsOpponent instanceof AbstractOmniCollection && !(notEqualsOpponent instanceof OmniList.OfRef)) {
                                                    switch(initParams.collectionType) {
                                                    case BOOLEAN:
                                                        if(!(notEqualsOpponent instanceof OmniList.OfBoolean)) {
                                                            break goToThrowOneDirection;
                                                        }
                                                        break;
                                                    case BYTE:
                                                        if(!(notEqualsOpponent instanceof OmniList.OfByte)) {
                                                            break goToThrowOneDirection;
                                                        }
                                                        break;
                                                    case CHAR:
                                                        if(!(notEqualsOpponent instanceof OmniList.OfChar)) {
                                                            break goToThrowOneDirection;
                                                        }
                                                        break;
                                                    case DOUBLE:
                                                        if(!(notEqualsOpponent instanceof OmniList.OfDouble)) {
                                                            break goToThrowOneDirection;
                                                        }
                                                        break;
                                                    case FLOAT:
                                                        if(!(notEqualsOpponent instanceof OmniList.OfFloat)) {
                                                            break goToThrowOneDirection;
                                                        }
                                                        break;
                                                    case INT:
                                                        if(!(notEqualsOpponent instanceof OmniList.OfInt)) {
                                                            break goToThrowOneDirection;
                                                        }
                                                        break;
                                                    case LONG:
                                                        if(!(notEqualsOpponent instanceof OmniList.OfLong)) {
                                                            break goToThrowOneDirection;
                                                        }
                                                        break;
                                                    case SHORT:
                                                        if(!(notEqualsOpponent instanceof OmniList.OfShort)) {
                                                            break goToThrowOneDirection;
                                                        }
                                                    case REF:
                                                        break;
                                                    default:
                                                        throw initParams.collectionType.invalid();
                                                    }
                                                }
                                                Assertions.assertThrows(illegalMod.expectedException,()->thisList.equals(notEqualsOpponent));  
                                                Assertions.assertThrows(illegalMod.expectedException,()->notEqualsOpponent.equals(thisList));
                                                thisListMonitor.verifyCollectionState();
                                                thisListMonitor.repairModCount();
                                                continue outer;
                                            }
                                            Assertions.assertThrows(illegalMod.expectedException,()->thisList.equals(notEqualsOpponent));  
                                            Assertions.assertFalse(notEqualsOpponent.equals(thisList));
                                            thisListMonitor.verifyCollectionState();
                                            thisListMonitor.repairModCount();
                                            continue outer;
                                        }
                                        Assertions.assertFalse(thisList.equals(notEqualsOpponent));    
                                        if(notEqualsOpponent!=null) {
                                            if(notEqualsOpponent instanceof List && !(notEqualsOpponent instanceof AbstractOmniCollection)) {
                                                Assertions.assertThrows(illegalMod.expectedException,()->notEqualsOpponent.equals(thisList));
                                            }else {
                                                Assertions.assertFalse(notEqualsOpponent.equals(thisList));
                                            }                   
                                        }
                                        thisListMonitor.verifyCollectionState();
                                        thisListMonitor.repairModCount();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("SequenceEqualityTest.testequals_Object");
    }
}
