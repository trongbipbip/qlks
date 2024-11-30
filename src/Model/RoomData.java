package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoomData {
    public static final Map<String, List<Integer>> ROOM_MAP = new HashMap<>() {{
        put("Single Room", List.of(101, 201, 301, 401));
        put("Double Room", List.of(102, 202, 302, 402));
        put("Suite", List.of(104, 204));
    }};

}
