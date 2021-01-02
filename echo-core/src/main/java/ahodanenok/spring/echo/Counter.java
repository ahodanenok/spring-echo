package ahodanenok.spring.echo;

public class Counter {

    private int requestCount;
    private int errorCount;

    public void countRequest() {
        requestCount++;
    }

    public void countError() {
        errorCount++;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public int getErrorCount() {
        return errorCount;
    }
}
