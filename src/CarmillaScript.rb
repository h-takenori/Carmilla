class CarmillaScript
  
  #select
  def select(list , block)
    list.select{|a|
      eval block 
    }
  end
  
  def sort(list , block)
    list.sort{|a,b|
      eval block
    }
  end
end