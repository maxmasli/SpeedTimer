package masli.prof.cubinghelper;

import java.util.ArrayList;
import java.util.List;

public class CubingHelper {

    private static CubingHelper sCubingHelper;

    public static final String event_333 = "333";
    public static final String event_222 = "222";

    public static String currentEvent = event_333;

    List<Result> results333;
    List<Result> results222;

    private String scramble;

    //синглтон (кал)
    public static CubingHelper get() {
        if (sCubingHelper == null) {
            sCubingHelper = new CubingHelper();
        }
        return sCubingHelper;
    }


    //конструктор
    public CubingHelper() {
        results333 = new ArrayList<>();
        results222 = new ArrayList<>();
    }

    //геттеры сеттеры
    public List<Result> getResults333() {
        return results333;
    }

    public void setResults333(List<Result> results333) {
        this.results333 = results333;
    }

    public List<Result> getResults222() {
        return results222;
    }

    public void setResults222(List<Result> results222) {
        this.results222 = results222;
    }

    public String getScramble() {
        return scramble;
    }

    //методы работы со списками
    public void addResult(Double result, String scr, String event) {
        Result r = new Result(result, scr);
        switch (event) {
            case event_333:
                results333.add(r);
                break;
            case event_222:
                results222.add(r);
                break;
        }
    }

    public void removeResult(Double result, String scr, String event) {
        if (results333.size() <= 0) {
            return;
        }
        switch (event) {
            case event_333:
                for (Result n : results333) {
                    if (n.getScramble().equals(scr)) {
                        results333.remove(n);
                        break;
                    }
                }
                break;
            case event_222:
                for (Result n : results222) {
                    if (n.getScramble().equals(scr)) {
                        results222.remove(n);
                        break;
                    }
                }
                break;
        }
    }

    public void removeLastResult(String event) {
        if (results333.size() <= 0) {
            return;
        }
        switch (event) {
            case event_333:
                results333.remove(results333.size() - 1);
                break;
            case event_222:
                results222.remove(results222.size() - 1);
                break;
        }
    }

    //методы для генерации скрамблов
    public String get333RandomScramble() {
        String[][] lit = new String[][]{
                {"F", "F'", "F2"},
                {"B", "B'", "B2"},
                {"U", "U'", "U2"},
                {"D", "D'", "D2"},
                {"R", "R'", "R2"},
                {"L", "L'", "L2"},
        };

        String res = "";

        List<String> scr = new ArrayList<>();
        List<Integer> banned = new ArrayList<>();

        while (true) {
            int i = (int) (Math.random() * 6);
            int j = (int) (Math.random() * 3);

            if ((scr.size() > 2 && scr.get(scr.size() - 2) == lit[i][j]) || banned.contains(i)) {
                continue;
            }
            scr.add(lit[i][j]);

            if ((i % 2 != 0 && banned.contains(i - 1)) || (i % 2 == 0 && banned.contains(i + 1))) {
                banned.add(i);
            } else {
                banned.clear();
                banned.add(i);
            }

            if (scr.size() >= 20) {
                break;
            }
        }

        for (String l : scr) {
            res += l + " ";
        }
        scramble = res;
        return res;
    }

    public String get222RandomScramble() {

        List<String> scr = new ArrayList<>();
        int temp = -1;
        String res = "";

        String[][] lit = new String[][]{
                {"R", "R'", "R2"},
                {"F", "F'", "F2"},
                {"U", "U'", "U2"},
        };

        while (true) {
            int i = (int) (Math.random() * 3);
            int j = (int) (Math.random() * 3);
            if (temp == i) {
                continue;
            } else {
                temp = i;
                scr.add(lit[i][j]);
            }
            if (scr.size() > 9) {
                break;
            }
        }

        for (String l : scr) {
            res += l + " ";
        }

        scramble = res;
        return res;
    }

    // методы для высчитывания авг (если возвращает null значит сборок мало для высчитываения)
    public Object countAvg5(List<Result> l) {
        if (l.size() < 5) {
            return null;
        }
        List<Result> listNew = l.subList(l.size() - 5, l.size());
        List<Result> list = new ArrayList<>();

        list.addAll(listNew);

        
        int dnfs = 0;
        for (Result d : list) {
            if (d.getTime() < 0) {
                dnfs++;
                continue;
            }
        }

        if (dnfs > 1) {
            return null;
        }

        list.remove(getMax(list));
        list.remove(getMin(list));

        double sum = 0;
        for (Result d : list) {
            sum += d.getTime();
        }

        return Math.round(sum * 100 / 3) / 100.0d;
    }

    public Object countAvg12(List<Result> l) {
        if (l.size() < 12) {
            return null;
        }
        List<Result> listNew = l.subList(l.size() - 12, l.size());
        List<Result> list = new ArrayList<>();

        list.addAll(listNew);

        int dnfs = 0;
        for (Result d : list) {
            if (d.getTime() < 0) {
                dnfs++;
                continue;
            }
        }

        if (dnfs > 1) {
            return null;
        }

        list.remove(getMax(list));
        list.remove(getMin(list));

        double sum = 0;
        for (Result d : list) {
            sum += d.getTime();
        }

        return Math.round(sum * 100 / 10) / 100.0d;
    }

    public Object countAvg50(List<Result> l) {
        if (l.size() < 50) {
            return null;
        }
        List<Result> listNew = l.subList(l.size() - 50, l.size());
        List<Result> list = new ArrayList<>();

        list.addAll(listNew);

        int dnfs = 0;
        for (Result d : list) {
            if (d.getTime() < 0) {
                dnfs++;
                continue;
            }
        }

        if (dnfs > 1) {
            return null;
        }

        list.remove(getMax(list));
        list.remove(getMin(list));

        double sum = 0;
        for (Result d : list) {
            sum += d.getTime();
        }

        return Math.round(sum * 100 / 48) / 100.0d;
    }

    public Object countAvg100(List<Result> l) {
        if (l.size() < 100) {
            return null;
        }
        List<Result> listNew = l.subList(l.size() - 100, l.size());
        List<Result> list = new ArrayList<>();

        list.addAll(listNew);

        int dnfs = 0;
        for (Result d : list) {
            if (d.getTime() < 0) {
                dnfs++;
                continue;
            }
        }

        if (dnfs > 1) {
            return null;
        }

        list.remove(getMax(list));
        list.remove(getMin(list));

        double sum = 0;
        for (Result d : list) {
            sum += d.getTime();
        }

        return Math.round(sum * 100 / 98) / 100.0d;
    }

    //внутренние методы
    public static Result getMin(List<Result> li) {
        Result min = li.get(0);
        for (Result d : li) {
            if (d.getTime() < min.getTime() && d.getTime() != -1.00) {
                min = d;
            }
        }
        return min;
    }

    public static Result getMax(List<Result> li) {

        Result max = li.get(0);
        for (Result d : li) {
            if (d.getTime() == -1.00) {
                return d;
            } else if (d.getTime() > max.getTime()) {
                max = d;
            }
        }
        return max;
    }
}


