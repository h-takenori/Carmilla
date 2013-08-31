class CarmillaRb
  def select(list , block)
    list.select{|x|
      p x, block
      eval block
    }
  end
end