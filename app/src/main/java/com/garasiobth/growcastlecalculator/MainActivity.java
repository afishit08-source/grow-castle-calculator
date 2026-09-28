package com.garasiobth.growcastlecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root,result; EditText target; Spinner mode,build;
    final String[] modes={"Push Wave","Colony","Dungeon","Skill Point","Towers","Database"};
    final String[] builds={"Lightning","Physical","Fire","Ice","Poison","Summon","Gold/Farming","Custom"};
    static class U {String n,role;double r;U(String n,String role,double r){this.n=n;this.role=role;this.r=r;}}
    final Map<String,U[]> db=new LinkedHashMap<>();
    { 
      db.put("Lightning",new U[]{u("Town Archer","Core",.10),u("Castle","Core",.045),u("Zeus","DPS",.04),u("Thor","DPS",.04),u("Dark Lightning Archer","DPS",.04),u("Worm / Thorn Worm","DPS",.03),u("Dark Elf","Support",.02),u("Edward","Support",.03),u("Sniper","DPS",.035),u("Dark Skeleton","Support",.02),u("Poison Dorothy","Support",.02),u("White Mage","Support",.02),u("Chrono","Support",.02),u("Dark Necro","Support",.02),u("Smith","Support",.02),u("Stone","Support",.02),u("Pure Wizard","Support",.02),u("Golem Master","Support",.02),u("Angel","Support",.02),u("Red Defender","Support",.02)});
      db.put("Physical",new U[]{u("Town Archer","Core",.10),u("Castle","Core",.06),u("Giant","DPS",.04),u("Dark Bow Master","DPS",.04),u("Sniper","DPS",.035),u("Edward","Support",.03),u("Dark Elf","Support",.02),u("Dark Skeleton","Support",.02),u("Slinger","Support",.02),u("Angel","Support",.02),u("White Mage","Support",.02),u("Chrono","Support",.02),u("Dark Necro","Support",.02),u("Smith","Support",.02),u("Stone","Support",.02),u("Pure Wizard","Support",.02),u("Poison Dorothy","Support",.02)});
      db.put("Fire",new U[]{u("Town Archer","Core",.10),u("Castle","Core",.06),u("Flame","DPS",.04),u("Fire Wizard","DPS",.04),u("Dark Elf","Support",.02),u("Edward","Support",.03),u("White Mage","Support",.02),u("Chrono","Support",.02),u("Dark Necro","Support",.02),u("Smith","Support",.02),u("Stone","Support",.02),u("Pure Wizard","Support",.02)});
      db.put("Ice",new U[]{u("Town Archer","Core",.10),u("Castle","Core",.06),u("Frozen","DPS",.04),u("Dark Ice Wizard","DPS",.04),u("Ice Wizard","DPS",.04),u("Dark Elf","Support",.02),u("White Mage","Support",.02),u("Chrono","Support",.02),u("Dark Necro","Support",.02),u("Smith","Support",.02),u("Stone","Support",.02)});
      db.put("Poison",new U[]{u("Town Archer","Core",.10),u("Castle","Core",.06),u("Poison Dorothy","DPS",.04),u("Poison","DPS",.04),u("Dark Elf","Support",.02),u("Edward","Support",.03),u("White Mage","Support",.02),u("Chrono","Support",.02),u("Dark Necro","Support",.02),u("Smith","Support",.02),u("Stone","Support",.02)});
      db.put("Summon",new U[]{u("Town Archer","Core",.10),u("Castle","Core",.06),u("Dark Skeleton","DPS",.04),u("Golem Master","DPS",.04),u("Angel","Support",.03),u("Dark Elf","Support",.02),u("White Mage","Support",.02),u("Chrono","Support",.02),u("Dark Necro","Support",.02),u("Smith","Support",.02),u("Stone","Support",.02)});
      db.put("Gold/Farming",new U[]{u("Town Archer","Core",.10),u("Castle","Core",.06),u("Edward","Support",.03),u("Dark Elf","Support",.02),u("White Mage","Support",.02),u("Chrono","Support",.02),u("Dark Necro","Support",.02),u("Smith","Support",.02),u("Stone","Support",.02)});
      db.put("Custom",new U[]{u("Town Archer","Core",.10),u("Castle","Core",.06),u("Custom DPS","DPS",.04),u("Custom Support","Support",.02)});
    }
    U u(String n,String role,double r){return new U(n,role,r);}
    TextView t(String s,int size,boolean bold){TextView x=new TextView(this);x.setText(s);x.setTextColor(Color.WHITE);x.setTextSize(size);x.setPadding(12,8,12,8);if(bold)x.setTypeface(Typeface.DEFAULT,Typeface.BOLD);return x;}
    @Override public void onCreate(Bundle b){super.onCreate(b);ui();calc();}
    void ui(){ScrollView sc=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(16,16,16,24);root.setBackgroundColor(Color.rgb(15,16,20));sc.addView(root);setContentView(sc);
      root.addView(t("GROW CASTLE CALCULATOR",24,true));root.addView(t("v1.50.14 • Android • Offline",13,false));
      root.addView(t("Mode",15,true));mode=new Spinner(this);mode.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,modes));root.addView(mode);
      root.addView(t("Build",15,true));build=new Spinner(this);build.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,builds));root.addView(build);
      root.addView(t("Target Wave / Level",15,true));target=new EditText(this);target.setText("1000");target.setTextColor(Color.WHITE);target.setInputType(InputType.TYPE_CLASS_NUMBER);root.addView(target);
      Button b=new Button(this);b.setText("HITUNG");root.addView(b);b.setOnClickListener(v->calc());
      mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){public void onNothingSelected(AdapterView<?>p){}public void onItemSelected(AdapterView<?>p,android.view.View v,int a,long c){calc();}});
      build.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){public void onNothingSelected(AdapterView<?>p){}public void onItemSelected(AdapterView<?>p,android.view.View v,int a,long c){calc();}});
      result=new LinearLayout(this);result.setOrientation(LinearLayout.VERTICAL);root.addView(result);
    }
    int n(){try{return Math.max(1,Integer.parseInt(target.getText().toString().trim()));}catch(Exception e){return 1;}}
    int ceil(double x){return (int)Math.max(1,Math.ceil(x));}
    void calc(){if(result==null)return;result.removeAllViews();int x=n();String m=(String)mode.getSelectedItem(),b=(String)build.getSelectedItem();result.addView(t(m+" • "+b,19,true));
      if(m.equals("Database")){for(Map.Entry<String,U[]> e:db.entrySet()){result.addView(t(e.getKey(),17,true));for(U z:e.getValue())result.addView(t(z.n+"  |  "+z.role+"  |  ratio "+z.r,13,false));}result.addView(t("Patch 1.50.x: Thorn Worm promotion, Bazooka/Cannoneer promotion changes, tower item support, Town Archer item-stat changes, Devil's Horn +5.",12,false));return;}
      if(m.equals("Skill Point")){int[] c={250,500,750,1000,1250,1500,2000,3000,4000,5000,7000,9000,11000,14000,17000,20000,25000,29000,34000,40000};int p=0,next=-1;for(int q:c){if(x>=q)p++;else{next=q;break;}}result.addView(t("Checkpoint skill points reached: "+p,16,true));result.addView(t("Next checkpoint: "+(next<0?"> 40,000":next),14,false));result.addView(t("Skill allocation remains build-specific; this screen does not invent a universal optimal tree.",12,false));return;}
      if(m.equals("Colony")){result.addView(t("Colony target: "+x,16,true));result.addView(t("Infinite Colony planner baseline: defense ratio 3.0× target.",14,false));result.addView(t("Community ratios are recommendations, not official game formulas.",12,false));return;}
      if(m.equals("Dungeon")){result.addView(t("Dungeon target: "+x,16,true));result.addView(t("Dungeon uses stage/rune requirements; enter the stage you want to plan around.",14,false));return;}
      if(m.equals("Towers")){result.addView(t("Tower planner • target wave "+x,16,true));result.addView(t("Towers can equip items in 1.50.x and require the relevant skill-tree activation.",13,false));result.addView(t("Main tower baseline: "+ceil(x*.04),15,true));result.addView(t("Support/utility baseline: "+ceil(x*.02),15,true));return;}
      for(U z:db.get(b)){int lv=ceil(x*z.r);if((z.n.equals("Chrono")||z.n.equals("Dark Necro")||z.n.equals("Smith"))&&x<1000)lv=Math.min(lv,21);result.addView(t(z.n+"  —  "+lv+"  ["+z.role+", "+z.r+"]",14,false));}
      result.addView(t("Formula: target level = ceil(target wave × community ratio).",12,false));result.addView(t("Official 1.50.14 patch facts are separated from community ratio recommendations.",12,false));
    }
}
