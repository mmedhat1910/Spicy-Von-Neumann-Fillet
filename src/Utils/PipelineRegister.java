package Utils;

public class PipelineRegister<T>{
    private T newBlock;
    private T oldBlock;



    public T getOldBlock() {
        return oldBlock;
    }
    public void setNewBlock(T block){
        this.newBlock = block;
    }

    public void propagate(){
        this.oldBlock = this.newBlock;
        this.newBlock = null;
    }

    public void flush(){
        this.newBlock = null;
        this.oldBlock = null;
    }

    public String toString(){
        return "New: " + this.oldBlock + "\nOld: " + this.newBlock;
    }


    public T getNewBlock() {
        return newBlock;
    }
}
