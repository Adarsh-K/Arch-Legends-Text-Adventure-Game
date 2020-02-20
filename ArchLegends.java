import java.util.*;
class Location{
	private final int Index;
	private final int Depth;
	private Monster _Monster;
	private final ArrayList<Location> All_Neighs;
	private Location Prev_Location;
	public Location(int Depth, int Index){
		this.Index=Index;
		this.Depth=Depth;
		this.All_Neighs=new ArrayList<Location>();
	}
	public int get_Index(){
		return this.Index;
	}
	public boolean is_Neigh(Location Neigh){
		for(int i=0;i<this.All_Neighs.size();i++){
			if(this.All_Neighs.get(i)==Neigh){
				return true;
			}
		}
		return false;
	}
	public int get_Depth(){
		return this.Depth;
	}
	public Location get_Prev(){
		return this.Prev_Location;
	}
	public Monster get_Monster(){
		return this._Monster;
	}
	public float get_HP(){
		return this._Monster.get_HP();
	}
	public int get_Level(){
		return this._Monster.get_Level();
	}
	public float get_Damage(float Hero_HP){
		return this._Monster.do_Damage(Hero_HP);
	}
	public ArrayList<Location> get_Neighs(){
		return this.All_Neighs;
	}
	public void set_Neigh(Location Neigh){
		for(int i=0;i<this.All_Neighs.size();i++){
			if(this.All_Neighs.get(i)==Neigh){
				return;	
			}
		}
		this.All_Neighs.add(Neigh);
	}
	public void set_Monster(Monster _Monster){
		this._Monster=_Monster;
	}
	public void set_Prev_Location(Location Prev_Location){
		this.Prev_Location=Prev_Location;
	}
}
class Kingdom{
	private final Location[] All_Locations;
	public Kingdom(){
		Random rand=new Random();
		int num_locations=rand.nextInt(5)+16+1+1;// Lionfang and Starting
		this.All_Locations=new Location[num_locations];
	}
	public Location[] get_All_Locations(){
		return this.All_Locations;
	}
	public void set_Lionfang_Neighs(Random rand){
		int num_locations=this.All_Locations.length;
		Location _Lionfang=this.All_Locations[num_locations-1];
		for(int i=0;i<4;i++){//set neighbours of Lionfang(4)
			int index_temp_Lionfang_Neigh=rand.nextInt(num_locations-2)+1;
			Location temp_Lionfang_Neigh=this.All_Locations[index_temp_Lionfang_Neigh];
			while(temp_Lionfang_Neigh!=null){
				index_temp_Lionfang_Neigh=rand.nextInt(num_locations-2)+1;
				temp_Lionfang_Neigh=this.All_Locations[index_temp_Lionfang_Neigh];
			}
			temp_Lionfang_Neigh=new Location(3, index_temp_Lionfang_Neigh-1);
			temp_Lionfang_Neigh.set_Neigh(_Lionfang);
			_Lionfang.set_Neigh(temp_Lionfang_Neigh);
		}
	}
	public void set_Neighs_Depth(int Depth, Random rand){
		int num_locations=this.All_Locations.length;
		ArrayList<Location> same_depth_Locations=new ArrayList<Location>();
		for(int i=1;i<num_locations-1;i++){
			if(this.All_Locations[i]!=null&&this.All_Locations[i].get_Depth()==Depth){
				same_depth_Locations.add(this.All_Locations[i]);
			}
		}
		if(Depth==1){
			for(int i=1;i<this.All_Locations.length-1;i++){
				if(this.All_Locations[i]==null){
					All_Locations[i]=new Location(1,i-1);
					same_depth_Locations.add(All_Locations[i]);
				}
			}
			for(int j=0;j<same_depth_Locations.size();j++){
				Location temp=same_depth_Locations.get(j);
				this.All_Locations[0].set_Neigh(temp);
			}		

			for(int j=0;j<same_depth_Locations.size();j++){
				int index_same_Neigh=rand.nextInt(same_depth_Locations.size());
				while(index_same_Neigh==j||same_depth_Locations.get(j).
				is_Neigh(same_depth_Locations.get(index_same_Neigh))){// 1 Neigh in same depth
					index_same_Neigh=rand.nextInt(same_depth_Locations.size());
				}
				same_depth_Locations.get(j).set_Neigh(same_depth_Locations.get(index_same_Neigh));
				same_depth_Locations.get(index_same_Neigh).set_Neigh(same_depth_Locations.get(j));
			}
		}
		else{
			for(int j=0;j<same_depth_Locations.size();j++){
				for(int k=0;k<3;k++){// 3 Neighs in lower depth ie 2
					int index_temp_Neigh=rand.nextInt(num_locations-2)+1;
					Location temp_Neigh=this.All_Locations[index_temp_Neigh];
					while(temp_Neigh!=null&&temp_Neigh.get_Depth()<Depth){
						index_temp_Neigh=rand.nextInt(num_locations-2)+1;
						temp_Neigh=this.All_Locations[index_temp_Neigh];
					}
					if(temp_Neigh==null){
						temp_Neigh=new Location(Depth-1, index_temp_Neigh-1);
					}
					temp_Neigh.set_Neigh(same_depth_Locations.get(j));
					same_depth_Locations.get(j).set_Neigh(temp_Neigh);
				}
			}// Total atleast 4 Neighs.!!	
		}	
		for(int j=0;j<same_depth_Locations.size();j++){
			int index_same_Neigh=rand.nextInt(same_depth_Locations.size());
			while(index_same_Neigh==j||same_depth_Locations.get(j).
			is_Neigh(same_depth_Locations.get(index_same_Neigh))){// 1 Neigh in same depth
				index_same_Neigh=rand.nextInt(same_depth_Locations.size());
			}
			same_depth_Locations.get(j).set_Neigh(same_depth_Locations.get(index_same_Neigh));
			same_depth_Locations.get(index_same_Neigh).set_Neigh(same_depth_Locations.get(j));
		}
	}
	public void set_Depth_3_Neighs(Random rand){
		this.set_Neighs_Depth(3, rand);
	}
	public void set_Depth_2_Neighs(Random rand){
		this.set_Neighs_Depth(2, rand);
	}
	public void set_Depth_1_Neighs(Random rand){
		this.set_Neighs_Depth(1,rand);
	}
	public void set_Monster(Random rand){
		int num_locations=this.All_Locations.length;
		for(int i=1;i<num_locations-1;i++){
			Location temp=this.All_Locations[i];
			int option_Monster=rand.nextInt(3)+1;
			if(option_Monster==1){
				temp.set_Monster(new Goblins());
			}
			else if(option_Monster==2){
				temp.set_Monster(new Zombies());
			}
			else{
				temp.set_Monster(new Fiends());
			}
		}

	}
	public void create_Kingdom(){
		Random rand=new Random();
		int num_locations=this.All_Locations.length;
		Location _Lionfang=new Location(4,num_locations-2);
		_Lionfang.set_Monster(new Lionfang());
		this.All_Locations[num_locations-1]=_Lionfang;//Lionfang set to end
		Location _Starting=new Location(0,-1);
		_Starting.set_Monster(null);/////////// Check if correct
		this.All_Locations[0]=_Starting;//Starting set to start
		this.set_Lionfang_Neighs(rand);
		this.set_Depth_3_Neighs(rand);
		this.set_Depth_2_Neighs(rand);
		this.set_Depth_1_Neighs(rand);
		this.set_Monster(rand);
	}
}
class Play_Game{
	public void Menu_Choose_Path(Location[] All_Locations, Hero _Hero){
		Location Current_Location=All_Locations[0];
		Scanner scan=new Scanner(System.in);
		int s=-1;
		while(true){
			int index=Current_Location.get_Index();
			String string_Current_Location="";
			if(index==-1){
				string_Current_Location="the starting location";
			}
			else{
				string_Current_Location="location"+String.valueOf(index);
			}
			System.out.printf("You are at %s. Choose path:\n",string_Current_Location);
			ArrayList<Location> Path=Current_Location.get_Neighs();
			for(int i=0;i<Path.size();i++){
				Location temp_Neigh=Path.get(i);
				System.out.printf("%d) Go to Location %d\n",i+1,temp_Neigh.get_Index());
			}
			if(s!=-1){
				System.out.printf("%d) Go back\n",Path.size()+1);
			}
			System.out.printf("Enter -1 to exit\n");
			int option_Path=Integer.valueOf(scan.next());
			if(option_Path==-1){
				break;
			}
			else if(option_Path>Path.size()){
				Location Prev_Location=Current_Location;
				Current_Location=Current_Location.get_Prev();
				Current_Location.set_Prev_Location(Prev_Location);
			}
			else{
				for(int i=0;i<Path.size();i++){
					if(option_Path==i+1){
						Location Prev_Location=Current_Location;
						Path.get(i).set_Prev_Location(Prev_Location);
						Current_Location=Path.get(i);
						System.out.printf("Moving to location %d\n",Current_Location.get_Index());
						System.out.printf("Fight started. You're fighting a level %d Monster.\n",
							Current_Location.get_Level());
						int next=this.Menu_Choose_Move(Path.get(i),_Hero,scan);
						if(next==-1){
							Current_Location=All_Locations[0];
							break;
						}
						else{
							break;
						}
					}
				}
			}
		}
	}
	public int Menu_Choose_Move(Location Current_Location,Hero _Hero, Scanner scan){
		Monster _Monster=Current_Location.get_Monster();
		boolean flag=false;
		int flag_counter=0;
		int counter=0;
		while(true){
			if(_Hero.get_HP()<=0){
				_Hero.reset();
				_Monster.reincarnation();
				return -1;
			}
			counter++;
			boolean defense=false;
			System.out.println("Choose move:");
			System.out.printf("1) Attack\n2) Defense\n");
			if(counter==3){
				System.out.println("3) Special Attack");
			}
			int option_Move=Integer.valueOf(scan.next());
			if(option_Move==1){
				if(counter==3){
					counter--;
				}
				System.out.println("You choose to attack");
				int damage=this.option_1(_Hero,_Monster,flag);
				System.out.printf("You attacked and inflicted %d damage to the monster.\n",damage);
				System.out.printf("Your Hp: %f/%d Monster's Hp: %f/%d\n",_Hero.get_HP(),
					_Hero.get_max_HP(),_Monster.get_HP(),_Monster.get_max_HP());
			}
			else if(option_Move==2){
				if(counter==3){
					counter--;
				}
				System.out.println("You choose to defend");
				defense=true;
				System.out.printf("Monster attack reduced by %d!\n",this.option_2(_Hero,_Monster,flag));/////				
			}
			else{
				counter=0;
				System.out.println("Special power activated");
				System.out.println("Performing special attack");
				flag=true;
			}
			if(flag){
				flag_counter++;
				if(flag_counter==4){
					flag_counter=0;
					flag=false;
				}
				boolean pass=false;
				if(flag){
					if(flag_counter==1){
						pass=true;
						this.option_3(_Hero,_Monster,pass);
					}
					else{
						this.option_3(_Hero,_Monster,pass);
					}
				}
			}
			if(Current_Location.get_HP()<=0){
				_Hero.reset();
				_Monster.reincarnation();
				System.out.println("Monster killed!");
				_Hero.update_XP(_Monster.get_Level());
				return 0;
			}
			float monsters_damage=this.Monster_Attack(_Hero,Current_Location,defense,flag);
			System.out.println("Monster attack!");
			System.out.printf("The monster attacked and inflicted %f damage to you.\n",
				monsters_damage);
			System.out.printf("Your Hp: %f/%d Monster's Hp: %f/%d\n",_Hero.get_HP(),
				_Hero.get_max_HP(),_Monster.get_HP(),_Monster.get_max_HP());
		}
	}
	public float Monster_Attack(Hero _Hero,Location Current_Location,boolean defense,boolean flag){
		float Hero_HP=_Hero.get_HP();
		float damage=Current_Location.get_Damage(Hero_HP);
		if(defense){
			damage=_Hero.defense(damage,flag);
		}
		_Hero.update_HP(damage);
		return damage;
	}
	public int option_1(Hero _Hero, Monster _Monster, boolean flag){
		return _Hero.attack(_Monster, flag);
	}
	public int option_2(Hero _Hero, Monster _Monster, boolean flag){
		return -(int)_Hero.defense(0,flag);
	}	
	public void option_3(Hero _Hero, Monster _Monster, boolean flag){
		_Hero.special_Power(_Monster,flag);
	}		
}
class User{
	private final String Username; 
	private final Hero _Hero;
	public User(String Username, Hero _Hero){
		this.Username=Username;
		this._Hero=_Hero;
	}
	public String get_Username(){
		return this.Username;
	}
	public Hero get_Hero(){
		return this._Hero;
	}
}
abstract class Hero{
	private int Level;//protected?
	private float HP;
	private int max_HP;
	private int XP;
	protected int Extra_point;
	private final String Type;
	public Hero(String Type){
		this.Level=1;
		this.HP=100;
		this.max_HP=100;
		this.XP=0;
		this.Extra_point=0;
		this.Type=Type;
	}
	public float get_HP(){
		return this.HP;
	}
	public void reset(){
		this.HP=this.max_HP;
	}
	public String get_Type(){
		return this.Type;
	}
	public int get_max_HP(){
		return this.max_HP;
	}
	public void update_HP(float damage){
		if(damage>0){
			this.HP-=damage;
		}
	}
	public void update_XP(int Monster_Level){
		this.XP+=(Monster_Level*20);
		this.update_Level();
	}
	public void inc_HP(float inc){
		this.HP+=inc;
	}
	public void update_Level(){ //private/protected?
		if(this.XP==20){
			this.Level=2;
			this.max_HP=150;
			this.HP=150;
			this.Extra_point++;
		}
		else if(this.XP==40){
			this.Level=3;
			this.max_HP=200;
			this.HP=200;
			this.Extra_point++;
		}
		else if(this.XP==60){
			this.Level=4;
			this.max_HP=250;
			this.HP=250;
			this.Extra_point++;
		}
		else{
			// Level remains 1
		}
		System.out.printf("%d XP awarded\nLevel Up: level: %d\n", this.XP, this.Level);
		System.out.println("Fight won proceed to the next location.");
	}
	protected abstract int attack(Monster _Monster, boolean flag);
	protected abstract float defense(float damage,boolean flag);
	protected abstract void special_Power(Monster _Monster,boolean flag);
}
class Warrior extends Hero{
	public Warrior(){
		super("Warrior");
	}
	@Override
	public int attack(Monster _Monster, boolean flag){
		int total_damage=10+this.Extra_point;
		if(flag){
			total_damage+=5;
		}
		_Monster.reduce_HP(total_damage);
		return total_damage;
	}	
	@Override
	public float defense(float damage,boolean flag){ //check implementation!!
		float total_damage=damage-(3+this.Extra_point);
		if(flag){
			total_damage-=5;
		}
		return total_damage;
	}
	@Override
	public void special_Power(Monster _Monster,boolean flag){
		System.out.println("Attack and Defense boosted by 5!");
	}
}
class Mage extends Hero{
	public Mage(){
		super("Mage");
	}
	@Override
	public int attack(Monster _Monster, boolean flag){
		int total_damage=5+this.Extra_point;
		_Monster.reduce_HP(total_damage);
		return total_damage;
	}	
	@Override
	public float defense(float damage,boolean flag){ //check implementation!!
		///////
		return damage-5;
	}
	@Override
	public void special_Power(Monster _Monster,boolean flag){
		float temp=_Monster.get_HP();
		temp*=0.05;
		_Monster.reduce_HP(temp);
		System.out.printf("Reduced Monster's Hp by %f\n",temp);
	}	
}

class Thief extends Hero{	
	public Thief(){
		super("Thief");
	}
	@Override
	public int attack(Monster _Monster, boolean flag){
		int total_damage=6+this.Extra_point;
		_Monster.reduce_HP(total_damage);
		return total_damage;
	}	
	@Override
	public float defense(float damage,boolean flag){ //check implementation!!
		///////
		return damage-4;
	}
	@Override
	public void special_Power(Monster _Monster,boolean flag){
		if(flag){
			float temp=_Monster.get_HP();
			temp*=0.3;
			this.inc_HP(temp);
			_Monster.reduce_HP(temp);
			System.out.printf("Stolen Monster's Hp by %f\n",temp);
		}
	}	
}

class Healer extends Hero{
	public Healer(){
		super("Healer");
	}
	@Override
	public int attack(Monster _Monster, boolean flag){
		int total_damage=4+this.Extra_point;
		_Monster.reduce_HP(total_damage);
		return total_damage;
	}	
	@Override
	public float defense(float damage,boolean flag){ //check implementation!!
		////////
		return damage-8;
	}
	@Override
	public void special_Power(Monster _Monster,boolean flag){
		float temp=this.get_HP();
		temp*=0.05;
		this.inc_HP(temp);
		System.out.printf("Increased own's Hp by %f\n",temp);
	}
}
class Monster{
	private float HP;
	private int max_HP;
	private final int Level;
	public Monster(int Level){
		this.Level=Level;
		if(this.Level==1){
			this.HP=100;
			this.max_HP=100;
		}
		else if(this.Level==2){
			this.HP=150;
			this.max_HP=150;
		}
		else if(this.Level==3){
			this.HP=200;
			this.max_HP=200;
		}
		else{
			this.HP=250;
			this.max_HP=250;
		}
	}
	public void reincarnation(){
		this.HP=this.max_HP;
	}
	public int get_Level(){
		return this.Level;
	}
	public float get_HP(){
		return this.HP;
	}
	public int get_max_HP(){
		return this.max_HP;
	}
	public void reduce_HP(float damage){
		if(damage>0){
			this.HP-=damage;
		}
		if(this.HP<=0){
			this.HP=0;
		}
	}
	public float do_Damage(float Hero_HP){
		Random rand=new Random();
		float damage=(float)rand.nextGaussian()+(this.HP/8);
		while(damage<0||damage>(HP/4)){
			damage=(float)rand.nextGaussian()+(this.HP/8);
		}
		return damage;
	}
}
class Goblins extends Monster{
	public Goblins(){
		super(1);
	}
}
class Zombies extends Monster{
	public Zombies(){
		super(2);
	}
}
class Fiends extends Monster{
	public Fiends(){
		super(3);
	}
}
class Lionfang extends Monster{
	public Lionfang(){
		super(4);
	}
	@Override
	public float do_Damage(float Hero_HP){
		float damage=super.do_Damage(Hero_HP);
		Random rand=new Random(10);
		if(rand.nextInt()==0){
			damage=Hero_HP/2;
		}
		return damage;
	}
}
class Main{
	private ArrayList<User> Users;
	public Main(){
		this.Users=new ArrayList<User>();
	}
	public void new_User(Scanner scan){
		System.out.println("Enter Username");
		String Username=scan.next();
		System.out.println("Choose a Hero");
		System.out.printf("1) Warrior\n2) Thief\n3) Mage\n4) Healer\n");
		int option_Hero=Integer.valueOf(scan.next());
		Hero _Hero=null;
		if(option_Hero==1){
			_Hero=new Warrior();
		}
		else if(option_Hero==2){
			_Hero=new Thief();
		}
		else if(option_Hero==3){
			_Hero=new Mage();
		}
		else{
			_Hero=new Healer();
		}
		User new_User=new User(Username, _Hero);
		this.Users.add(new_User);
		System.out.printf("User Creation done. Username: %s. Hero type: %s. Log in to play the game. Exiting\n", 
			Username, _Hero.get_Type());
	}
	public void existing_User(Kingdom _Kingdom,Scanner scan){
		System.out.println("Enter Username");
		String temp_Username=scan.next();
		User temp_User=null;
		for(int i=0;i<this.Users.size();i++){
			if(temp_Username.equals(this.Users.get(i).get_Username())){
				temp_User=this.Users.get(i);
				System.out.println("User Found... logging in");
				System.out.printf("Welcome %s\n", temp_User.get_Username());
				Play_Game new_Game=new Play_Game();
				new_Game.Menu_Choose_Path(_Kingdom.get_All_Locations(),temp_User.get_Hero());
				return;
			}
		}
		System.out.println("No such User!");
	}
	public void choose_Main_Option(Kingdom _Kingdom,Scanner scan){
		while(true){
			System.out.printf("Welcome to ArchLegends\nChoose your option\n");
			System.out.printf("1) New User\n2) Existing User\n3) Exit\n");
			int option_Main=Integer.valueOf(scan.next());
			if(option_Main==1){
				this.new_User(scan);
			}
			else if(option_Main==2){
				this.existing_User(_Kingdom,scan);
			}
			else{
				break;
			}
		}
	}
}
public class ArchLegends{
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		Kingdom new_Kingdom=new Kingdom();
		new_Kingdom.create_Kingdom();
		Main _Main=new Main();
		_Main.choose_Main_Option(new_Kingdom,scan);
	}
}