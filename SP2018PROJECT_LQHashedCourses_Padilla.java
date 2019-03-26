/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sp2018project_padilla;

/**
 *
 * @author francisco
 */
public class SP2018PROJECT_LQHashedCourses_Padilla 
{ 
    int N;
    int n = 0;			// the number of nodes in the structure
    int defaultQuotient = 9967;  	// the default 4k+3 prime
    double loadingFactor = 0.75;
    SP2018PROJECT_Course_Padilla  deleted;		// the dummy node, v2
    private SP2018PROJECT_Course_Padilla [] data; 	// the primary storage array

    public SP2018PROJECT_LQHashedCourses_Padilla (int length)
    { int pct = (int)((1.0/loadingFactor-1) *100.0);
      N = fourKPlus3(length, pct);
      data = new SP2018PROJECT_Course_Padilla [N];
      deleted = new SP2018PROJECT_Course_Padilla ("",null);
      for(int i = 0; i<N; i++)
         data[i] = null;
    }// end of constructor

    public boolean insert(SP2018PROJECT_Course_Padilla  newSP2018PROJECT_Course_Padilla )
    { boolean noError;
       boolean hit = false;
       int pass, q, offset, ip;
       int pk = stringToInt(newSP2018PROJECT_Course_Padilla .getKey());  // preprocess the key
       if( (((double) n)/N) < loadingFactor)// insert the node
       { pass = 0;
         q = pk / N;
         offset = q;
         ip = pk % N;
         if(q%N == 0)
             offset = defaultQuotient;
         while(pass < N)
         {  if(data[ip] == null || data[ip] == deleted)
            { hit = true;
               break;
            }
            ip = (ip + offset)%N;
            pass = pass +1;
         }
         if(hit == true)  // insert the node
         { data[ip]=newSP2018PROJECT_Course_Padilla .deepCopy( );
            n++;
            return noError = true;
         }
         else
            return noError = false;
       }
       else //structure full to loading factor
          return noError = false;
    }// end for the Insert method

    public SP2018PROJECT_Course_Padilla  fetch(String targetKey)
    {  boolean noError;
        boolean hit = false;
        int pass, q, offset, ip;
        int pk = stringToInt(targetKey);  // preprocess the key
        pass = 0;
        q = pk / N;
        offset = q ;
        ip = pk % N;
        if(q%N == 0)
           offset = defaultQuotient;
        while(pass < N)
         {  if(data[ip] == null) //node not in structure
                break;
            if(data[ip].compareTo(targetKey) == 0) //node found
            {  hit = true;
                break;
            }
            ip = (ip + offset)%N;  //collision occurred
            pass = pass +1;
         }
         if(hit == true) //return a deep copy of the node
            return data[ip].deepCopy();
         else
            return null;
    }//end of the Fetch method

    public boolean delete(String targetKey)
    {  boolean noError;
        boolean hit = false;
        int pass, q, offset, ip;
        int pk = stringToInt(targetKey);  // preprocess the key
        pass = 0;
        q = pk / N;
        offset = q;
        ip = pk % N;
        if(q%N == 0)
            offset = defaultQuotient;
        while(pass < N)
         { if(data[ip] == null) //node not in structure
               break;
            if(data[ip].compareTo(targetKey) == 0) // node found
            { hit = true;
               break;
            }
            ip = (ip + offset)%N;  //collision occurred
            pass = pass +1;
         }
      if(hit == true)  //delete the node
      {  data[ip] = deleted;
           n--;
           return noError = true;
      }
      else
          return noError = false;
    }//end of the Delete method

    public boolean update(String targetKey, SP2018PROJECT_Course_Padilla  newSP2018PROJECT_Course_Padilla )
    {  if(delete(targetKey) == false)
           return false;
       else if(insert(newSP2018PROJECT_Course_Padilla ) == false)
           return false;
       return true;
    }//end of the Update method

    public void showAll()
    {  for(int i= 0; i<N; i++)
        {if(data[i] != null && data[i] != deleted)
               System.out.print(data[i]+"\n");
        }
    }
    
    public SP2018PROJECT_Course_Padilla [] getAll()
    {
        return data;
    }
    
    public static int fourKPlus3(int n, int pct)  // from Figure 5.16
    {  boolean fkp3 = false;
        boolean aPrime = false;
        int prime, highDivisor, d;
        double pctd = pct;
        prime = (int)(n * (1.0 + (pctd/100.0)));  // guess the prime pct percent larger than n
        if(prime%2 == 0) //if even make the prime guess odd
        prime = prime +1;
       while(fkp3 == false) // not a 4k+3 prime
       {  while(aPrime == false)  // not a prime
           {  highDivisor = (int)(Math.sqrt(prime) + 0.5);
               for(d = highDivisor; d > 1; d--)
               { if(prime%d == 0)
            break;
               }
               if(d != 1) // prime not found
         prime = prime +2;
              else
         aPrime = true;
        }// end of the prime search loop
        if((prime-3)%4 == 0)
            fkp3 = true;
       else
       {  prime = prime +2;
           aPrime = false;
       }
    }// end of 4k+3 prime search loop
    return prime;
  }
    
    public static int stringToInt(String aKey)	// from Figure 5.18
    {  int pseudoKey = 0;
        int n = 1;
        int cn= 0;
        char c[] = aKey.toCharArray();
        int grouping =0;
        while (cn < aKey.length()) // there is still more character in the key
         {   grouping = grouping << 8;      // pack next four characters in i
             grouping = grouping + c[cn];
             cn = cn + 1;
             if (n==4 || cn == aKey.length()) // 4 characters are processed or no more characters
             {  pseudoKey = pseudoKey + grouping;
                 n = 0;
                 grouping = 0;
             }
             n = n + 1;
         }
         return Math.abs(pseudoKey);
    }
}
