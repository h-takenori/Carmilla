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

  def collect(list , block , result_class)
    import result_class
    list.collect{|a|
      eval block
    }
  end

  def group_by(list , block)
    list.group_by{|a|
      eval block
    }
  end
end