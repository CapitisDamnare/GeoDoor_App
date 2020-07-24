package tapsi.geodoor.retrofit.models;

public class AnswerModel {
    String answer;
    String data;

    public String getAnswer() {
        return answer;
    }

    public String getData() { return data; }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setData(String data) { this.data = data; }

    @Override
    public String toString() {
        return "AnswerModel{" +
                "answer='" + answer + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
