package Gson;

import java.util.List;

/**
 * Created by Fun on 2018/4/19.
 */

public class ShortEnglishToChinese {

    /**
     * word_name : good
     * is_CRI : 1
     * exchange : {"word_pl":["goods"]}
     */

    private String word_name;
    private String is_CRI;
    private ExchangeBean exchange;

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public String getIs_CRI() {
        return is_CRI;
    }

    public void setIs_CRI(String is_CRI) {
        this.is_CRI = is_CRI;
    }

    public ExchangeBean getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeBean exchange) {
        this.exchange = exchange;
    }

    public static class ExchangeBean {
        private List<String> word_pl;

        public List<String> getWord_pl() {
            return word_pl;
        }

        public void setWord_pl(List<String> word_pl) {
            this.word_pl = word_pl;
        }
    }
}
