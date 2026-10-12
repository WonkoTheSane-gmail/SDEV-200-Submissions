// FileName BowlingTeam.java
// Written by: Jonathon Meyer
// Written on: 10/04/2026

public class BowlingTeam
{
   private String teamName;
   private String[] members = new String[4];

   public void setTeamName(String team)
   {
      teamName = team;
   }
   
   public String getTeamName()
   {
      return teamName;
   }
   
   public void setMember(int number, String name)
   {
      members[number] = name;
   }
   
   public String getMember(int number)
   {
      return members[number];
   }
}