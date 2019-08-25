package groupbyday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GroupbyDay {

    public static void main(String[] args) throws Exception{
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendarM = Calendar.getInstance();
//        Calendar calendarP = Calendar.getInstance();
//        calendarM.setTime(format.parse("2018-05-01"));
//        calendarP.setTime(format.parse("2018-05-03"));
//        System.out.println(calendarP..compareTo(calendarM));

        List<BaseFinger> baseFingers = initBaseFingers();
        TreeMap<String, List<BaseFinger>> map = groupByDay(baseFingers);
        printMap(map);
        TreeMap<String, List<BaseFinger>> result = groupByRange(map);
        System.out.println("+++++++++++++++++++++++++++");
        printMap(result);
    }

    public static long calcDay(Calendar c1, Calendar c2){
        return (c1.getTime().getTime()-c2.getTime().getTime())/(24*60*60*1000);
    }
    public static TreeMap<String, List<BaseFinger>> groupByRange(TreeMap<String, List<BaseFinger>> map){
        TreeMap<String, List<BaseFinger>> result = new TreeMap<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendarM = null;
            int length = 0;
            Calendar calendarP;
            for(Map.Entry<String, List<BaseFinger>> entry : map.entrySet()){
                if(calendarM == null){
                    calendarM = Calendar.getInstance();
                    calendarM.setTime(format.parse(entry.getKey()));
                }
                calendarP = Calendar.getInstance();
                calendarP.setTime(format.parse(entry.getKey()));

                //不连续，重启Makr
                long sub = calcDay(calendarP, calendarM);
                if(sub > length){
                    calendarM.setTime(calendarP.getTime());
                }
                length ++ ;

                String key = format.format(calendarM.getTime());
                List<BaseFinger> list;
                if(result.containsKey(key)){
                    list = result.get(key);
                } else {
                    list = new ArrayList<>();
                }
                list.addAll(entry.getValue());
                result.put(key,list);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<BaseFinger> initBaseFingers(){
        String start = "2018-05-18";
        String end = "2018-05-22";
        return Arrays.asList(
                new BaseFinger(start, end, "text1.log"),
                new BaseFinger(start, start, "text2.log"),
                new BaseFinger(end, end, "text3.log"),
                new BaseFinger("2018-05-23", "2018-05-25", "text4.log"));
    }

    public static void printMap(TreeMap<String, List<BaseFinger>> map){
        map.forEach((key, value) ->{
            System.out.println(key + "->" + value);
        });
    }

    public static TreeMap<String, List<BaseFinger>> groupByDay(List<BaseFinger> baseFingers){
        TreeMap<String, List<BaseFinger>> map = new TreeMap<>();
        for (BaseFinger baseFinger : baseFingers){
            List<String> listDays = filtDay(baseFinger.getStart(), baseFinger.getEnd());
            for (String day : listDays) {
                List<BaseFinger> baseFingerList;
                if(map.containsKey(day)){
                    baseFingerList = map.get(day);
                } else {
                    baseFingerList = new ArrayList<>();
                }
                baseFingerList.add(baseFinger);
                map.put(day, baseFingerList);
            }
        }
        return map;
    }

    public static List<String> filtDay(String start, String end){
        List<String> days = new ArrayList<>();
        try {
            Calendar date1 = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dt1 = format.parse(start);
            date1.setTime(dt1);
            Date dt2 = format.parse(end);
            Calendar date2 = Calendar.getInstance();
            date2.setTime(dt2);
            days.add(start);
            while (!isSameDay(date1, date2)){
                date1.add(Calendar.DAY_OF_YEAR, 1);
                days.add(format.format(date1.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static boolean isSameDay(Calendar calDateA, Calendar calDateB) {

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }
}
