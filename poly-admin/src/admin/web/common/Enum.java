package admin.web.common;

public class Enum {
    public enum Status {       
    	IsLocked(1), IsActived(0);        
        public int value;

        private Status(int value) {
            this.value = value;
        }

    }
    public static String getStatus(int value) {
        if(value == Status.IsLocked.value) return "Hide";
        if(value == Status.IsActived.value) return "Show";
        
        return String.valueOf(value);
    }
    public enum Position {       
    	top(1), top_bot(2),bot_bot(3);        
        public int value;

        private Position(int value) {
            this.value = value;
        }

    }

    public static String getPosition(int value) {
        if(value == 1) return "Top";
        if(value == 2) return "Top-Bottom";
        if(value == 3) return "Bottom";
        return String.valueOf(value);
    }
    public enum ContentType {       
    	Header(1), Text(2),Title(3),Animation(4),Image(5),Column(6),Row(7),Grid(8),Tab(9),Text_Img(10),Button(11),Icon(12),Tile(13);        
        public int value;

        private ContentType(int value) {
            this.value = value;
        }

    }
    public static String getContentType(int value) {    	
        if(value == 1) return "Header";
        if(value == 2) return "Text";
        if(value == 3) return "Title";
        if(value == 4) return "Animation";
        if(value == 5) return "Image";
        if(value == 6) return "Column";
        if(value == 7) return "Row";
        if(value == 8) return "Grid";
        if(value == 9) return "Tab";
        if(value == 10) return "Text+Img";
        if(value == 11) return "Button";
        if(value == 12) return "Icon";
        if(value == 13) return "Tile";
        return String.valueOf(value);
    }
}