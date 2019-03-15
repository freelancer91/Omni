package omni.impl.seq;
abstract interface BooleanSubListDefault extends BooleanListDefault
{
  @Override
  public default boolean add(Boolean val){
    return add((boolean)val);
  }
}
