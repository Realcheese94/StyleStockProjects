package com.example.sj.stylestockprojects.Recommend.Crawlers20;

public class Find_site_by_url {
    private String input_url;
    private String output_url;
    private  String web_name;
    private String item_name;

    public Find_site_by_url(String item_name){
        this.item_name = item_name;
    }
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }



    public void setInput_url(String input_url) {
        this.input_url = input_url;
    }


    public String getOutput_url(){

        if (input_url.contains("imvely"))
        {
            output_url ="http://imvely.com/product/search.html?view_type=&supplier_code=&category_no=&search_type=product_name&keyword="+this.item_name;
        }
        else if(input_url.contains("merongshop"))
        {
            output_url = "http://merongshop.com/product/search.html?banner_action=&keyword="+this.item_name;
        }
        else if (input_url.contains("sonyunara"))
        {
            output_url = "https://www.sonyunara.com/search/search.php?orby=best&crema-query="+this.item_name+"&search_keyword="+this.item_name;
        }
        else if (input_url.contains("littleblack"))
        {
            output_url = "http://littleblack.co.kr/product/search.html?banner_action=&keyword="+this.item_name;
        }




        else {
            output_url = "";
        }
        return output_url;
    }


}
