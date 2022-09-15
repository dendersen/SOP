class Greetings
  def initialize(name = "World")
    @name = name.capitalize
  end
  def hi
    puts "Hello #{@name}"
  end
  def bye
    puts "Bye #{@name}, see you soon!"
  end
end

greetings = Greetings.new()
greetings.hi()
greetings.bye()
puts greetings.name