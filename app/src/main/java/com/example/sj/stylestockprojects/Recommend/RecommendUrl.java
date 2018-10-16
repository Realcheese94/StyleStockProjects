package com.example.sj.stylestockprojects.Recommend;

public class RecommendUrl {

    private int age;
    private String catagory;
    private String username;
    private String [] url10={"www.66girls.co.kr","www.sonyunara.com","www.hotping.co.kr","http://www.sappun.co.kr"," http://www.ggsing.com","www.merongshop.com","www.pinkelephant.co.kr","www.common-unique.com","www.beginning.kr","http://www.gosister.co.kr/"};

    private String[] Topurl20={"1","2","3"};
    private String[] Bottomurl20={""};
    private String[] Accurl20={""};
    private String[] Haturl20={""};
    private String[] Shoesurl20={""};

    private String[] url30;
    private String[] url40;
    private String gender;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public String[] geturl(){
        String [] result={};

        if(age>=10||age<20)
        {

        }
        else if(age>20||age<30){

        }
        else if(catagory.equals("acc")){
            //recommend_crawler.setShop_list(url30);
        }
        else if(catagory.equals("shoes")){
            //recommend_crawler.setShop_list(url40);
        }


        return result;
    }






    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
}

