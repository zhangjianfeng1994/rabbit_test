package com.sltas.example.java.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title: JAVA 8 STREAM
 * </p>
 * <p>
 * Description: Stream 的使用就是实现一个 filter-map-reduce 过程，产生一个最终结果，或者导致一个副作用（side
 * effect）
 * 
 * https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
 * 
 * https://blog.csdn.net/xuxiaoyinliu/article/details/73040808
 * 
 * 当你没有成功熟悉一个工具的时候，不要轻易去尝试新的东西
 * 
 * </p>
 * <p>
 * 
 * 流的操作类型分为两种： Intermediate：一个流可以后面跟随零个或多个 intermediate
 * 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，
 * 交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。 Terminal：一个流只能有一个
 * terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。Terminal 操作的执行，
 * 才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
 * 
 * 在对于一个 Stream 进行多次转换操作 (Intermediate 操作)，每次都对 Stream 的每个元素进行转换，而且是执行多次，
 * 这样时间复杂度就是 N（转换次数）个 for 循环里把所有操作都做掉的总和吗？其实不是这样的，转换操作都是 lazy 的， 多个转换操作只会在
 * Terminal 操作的时候融合起来，一次循环完成。我们可以这样简单的理解，Stream 里有个操作函数的集合，
 * 每次转换操作就是把转换函数放入这个集合中，在 Terminal 操作的时候循环 Stream 对应的集合，然后对每个元素执行所有的函数。
 * 
 * 
 * 流的操作 接下来，当把一个数据结构包装成 Stream 后，就要开始对里面的元素进行各类操作了。常见的操作可以归类如下。 Intermediate：
 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、
 * parallel、 sequential、 unordered Terminal： forEach、 forEachOrdered、 toArray、
 * reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、
 * findAny、 iterator Short-circuiting： anyMatch、 allMatch、 noneMatch、 findFirst、
 * findAny、 limit 我们下面看一下 Stream 的比较典型用法。
 * 
 * </p>
 * 
 * @author 周顺宇
 * @date 2018年9月13日 上午10:07:24
 */
public class JavaStream {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * <p>
	 * Title: streamConstructor
	 * </p>
	 * <p>
	 * Description: 构造流的几种常见方法
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月13日 上午10:15:41
	 */
	@Test
	public void streamConstructor() {

		// 1. Individual values
		Stream<String> stream = Stream.of("a", "b", "c");
		logger.info("Individual values : {}", stream);
		// 2. Arrays
		String[] strArray = new String[] { "a", "b", "c" };
		stream = Stream.of(strArray);
		stream = Arrays.stream(strArray);
		logger.info("Arrays : {}", stream);
		// 3. Collections
		List<String> list = Arrays.asList(strArray);
		stream = list.stream();
		logger.info("Collections : {}", stream);

	}

	/**
	 * <p>
	 * Title: intStreamConstructor
	 * </p>
	 * <p>
	 * Description: 数值流的构造
	 * 
	 * 需要注意的是，对于基本数值型，目前有三种对应的包装类型 Stream： IntStream、LongStream、DoubleStream。
	 * 当然我们也可以用 Stream<Integer>、Stream<Long> >、Stream<Double>，但是 boxing 和
	 * unboxing 会很耗时， 所以特别为这三种基本数值型提供了对应的 Stream。
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月13日 上午10:44:54
	 */
	@Test
	public void intStreamConstructor() {

		IntStream.of(new int[] { 1, 2, 3 }).forEach(System.out::println);
		System.out.println("==============================================");
		IntStream.range(1, 3).forEach(System.out::println);
		System.out.println("==============================================");
		IntStream.rangeClosed(1, 3).forEach(System.out::println);
		System.out.println("==============================================");

	}

	/**
	 * <p>
	 * Title: streamConvertObject
	 * </p>
	 * <p>
	 * Description: 流转换为其它数据结构
	 * 
	 * List<String> asList = stringStream.collect(ArrayList::new,
	 * ArrayList::add, ArrayList::addAll);
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月13日 上午10:49:33
	 */
	@Test
	public void streamConvertObject() {

		Stream<String> stream = Stream.of("a", "b", "c");
		// 1. Array
		String[] strArray1 = stream.toArray(String[]::new);
		for (String var : strArray1) {
			logger.info("String[] : {}", var);
		}
		logger.info("String[] ===========================");
		// 2. Collection
		stream = Stream.of("a", "b", "c");
		List<String> list1 = stream.collect(Collectors.toList());
		list1.forEach(c -> logger.info("List list1 : {}", c));
		logger.info("List list1 ===========================");

		stream = Stream.of("a", "b", "c");
		List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
		list2.forEach(c -> logger.info("List list2 : {}", c));
		logger.info("List list2 ===========================");

		stream = Stream.of("a", "b", "c");
		Set<String> set1 = stream.collect(Collectors.toSet());
		set1.forEach(c -> logger.info("Set set1 : {}", c));
		logger.info("Set set1 ===========================");

		stream = Stream.of("a", "b", "c");
		Stack<String> stack1 = stream.collect(Collectors.toCollection(Stack::new));
		stack1.forEach(c -> logger.info("Stack stack1 : {}", c));
		logger.info("Stack stack1 ===========================");

		// 3. String
		stream = Stream.of("a", "b", "c");
		String str = stream.collect(Collectors.joining()).toString();
		logger.info("String str ： {}", str);
		logger.info("String str  ===========================");

	}

	/**
	 * <p>
	 * Title: map
	 * </p>
	 * <p>
	 * Description: 转换大写
	 * 
	 * <R> Stream<R> map(Function<? super T,? extends R> mapper)
	 * 返回一个流，包括将给定函数应用到该流元素的结果。 这是一个intermediate operation。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月13日 下午2:28:00
	 */
	@Test
	public void map() {

		List<String> output = Stream.of("a", "b", "c").map(String::toUpperCase).collect(Collectors.toList());
		output.forEach(c -> logger.info("{}", c));

	}

	/**
	 * <p>
	 * Title: squareNums
	 * </p>
	 * <p>
	 * Description: 平方数
	 * 
	 * 这段代码生成一个整数 list 的平方数 {1, 4, 9, 16}。 从上面例子可以看出，map 生成的是个 1:1
	 * 映射，每个输入元素，都按照规则转换成为另外一个元素。还有一些场景，是一对多映射关系的，这时需要 flatMap。
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月13日 下午2:36:43
	 */
	@Test
	public void squareNums() {
		List<Integer> nums = Arrays.asList(1, 2, 3, 4);
		List<Integer> squareNums = nums.stream().map(n -> n * n).collect(Collectors.toList());
		squareNums.forEach(c -> logger.info("{}", c));
	}

	/**
	 * <p>
	 * Title: flatMap
	 * </p>
	 * <p>
	 * Description: flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终 output
	 * 的新 Stream 里面已经没有 List 了，都是直接的数字。
	 * 
	 * <R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>>
	 * mapper) 返回由将所提供的映射函数应用到每个元素的映射流的内容替换此流的每个元素的结果的结果流。每个映射流
	 * closed后其内容被放置到该流。（如果一个映射的流 null空流使用，而不是 这是一个intermediate operation。
	 * 
	 * 实例。 如果orders是源源不断的订单，每个订单包含一系列的项目，那么下面的生产流包含所有订单的所有行项目：
	 * orders.flatMap(order -> order.getLineItems().stream())...
	 * 如果path是一个文件的路径，然后生成包含在文件流的words： Stream<String> lines = Files.lines(path,
	 * StandardCharsets.UTF_8); Stream<String> words = lines.flatMap(line ->
	 * Stream.of(line.split(" +"))); 的 mapper函数传递给
	 * flatMap分割线，使用一个简单的正则表达式，为词的数组，然后创建从阵流的话。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月13日 下午2:46:10
	 */
	@Test
	public void flatMap() {
		Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
		Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
		outputStream.forEach(c -> logger.info("{}", c));
	}

	/**
	 * <p>
	 * Title: filter
	 * </p>
	 * <p>
	 * Description: 留下偶数 经过条件“被 2 整除”的 filter，剩下的数字为 {2, 4, 6}。
	 * 
	 * Stream<T> filter(Predicate<? super T> predicate)
	 * 返回由该流的元素组成的流，该元素与给定的谓词匹配。 这是一个intermediate operation。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月13日 下午2:57:19
	 */
	@Test
	public void filter() {
		Integer[] sixNums = { 1, 2, 3, 4, 5, 6 };
		Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
		for (Integer even : evens) {
			logger.info("{}", even);
		}

		// 把单词挑出来
		// List<String> output = reader.lines().
		// flatMap(line -> Stream.of(line.split(REGEXP))).
		// filter(word -> word.length() > 0).
		// collect(Collectors.toList());
		// 这段代码首先把每行的单词用 flatMap 整理到新的 Stream，然后保留长度不为 0 的，就是整篇文章中的全部单词了。

		List<String> output = Stream.of("Java8   stream is great").flatMap(line -> Stream.of(line.split(" ")))
				.filter(word -> word.length() > 0).collect(Collectors.toList());

		output.stream().forEach(c -> logger.info("{}", c));
	}

	/**
	 * <p>
	 * Title: forEach
	 * </p>
	 * <p>
	 * Description: 遍历
	 * 
	 * 对一个人员集合遍历，找出男性并打印姓名。可以看出来，forEach 是为 Lambda 而设计的，保持了最紧凑的风格。而且 Lambda
	 * 表达式本身是可以重用的，非常方便。当需要为多核系统优化时，可以
	 * parallelStream().forEach()，只是此时原有元素的次序没法保证，并行的情况下将改变串行时操作的行为，此时 forEach
	 * 本身的实现不需要调整，而 Java8 以前的 for 循环 code 可能需要加入额外的多线程逻辑。 但一般认为，forEach 和常规 for
	 * 循环的差异不涉及到性能，它们仅仅是函数式风格与传统 Java 风格的差别。 另外一点需要注意，forEach 是 terminal
	 * 操作，因此它执行后，Stream 的元素就被“消费”掉了，你无法对一个 Stream 进行两次 terminal 运算。下面的代码是错误的：
	 * stream.forEach(element -> doOneThing(element)); stream.forEach(element ->
	 * doAnotherThing(element));
	 * 
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月17日 上午10:46:59
	 */
	public void forEach() {

		// // Java 8
		// roster.stream()
		// .filter(p -> p.getGender() == Person.Sex.MALE)
		// .forEach(p -> System.out.println(p.getName()));
		// // Pre-Java 8
		// for (Person p : roster) {
		// if (p.getGender() == Person.Sex.MALE) {
		// System.out.println(p.getName());
		// }
		// }

	}

	/**
	 * <p>
	 * Title: parallelStream
	 * </p>
	 * <p>
	 * Description: 并行流
	 * 
	 * 1.0-1.4 中的 java.lang.Thread 5.0 中的 java.util.concurrent 6.0 中的 Phasers 等
	 * 7.0 中的 Fork/Join 框架 8.0 中的 Lambda
	 * 
	 * parallelStream其实就是一个并行执行的流.它通过默认的ForkJoinPool,可能提高你的多线程任务的速度
	 * 
	 * Stream具有平行处理能力，处理的过程会分而治之，也就是将一个大任务切分成多个小任务，这表示每个任务都是一个操作，因此像以下的程式片段：
	 * 
	 * forEach 不能修改自己包含的本地变量值，也不能用 break/return 之类的关键字提前结束循环。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月17日 上午10:56:59
	 */
	@Test
	public void parallelStream() {

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

		/**
		 * parallelStream其实就是一个并行执行的流.它通过默认的ForkJoinPool,可能提高你的多线程任务的速度
		 */
		numbers.parallelStream().forEach(System.out::print);

		System.out.println("\r\n==============================================");
		// 流
		numbers.stream().forEach(System.out::print);

		System.out.println("\r\n==============================================");

		/**
		 * 注意:如果forEachOrdered()中间有其他如filter()的中介操作，会试着平行化处理，然后最终forEachOrdered(
		 * )会以原数据顺序处理，因此，
		 * 使用forEachOrdered()这类的有序处理,可能会（或完全失去）失去平行化的一些优势，实际上中介操作亦有可能如此，例如sorted
		 * ()方法。
		 */
		numbers.parallelStream().forEachOrdered(System.out::print);

		/**
		 * 那么使用ThreadPoolExecutor或者ForkJoinPool，会有什么性能的差异呢？
		 * 首先，使用ForkJoinPool能够使用数量有限的线程来完成非常多的具有父子关系的任务，比如使用4个线程来完成超过200万个任务。但是，
		 * 使用ThreadPoolExecutor时，
		 * 是不可能完成的，因为ThreadPoolExecutor中的Thread无法选择优先执行子任务，需要完成200万个具有父子关系的任务时，
		 * 也需要200万个线程，显然这是不可行的。
		 * 
		 * PS: 不要将ThreadPoolExecutor与ForkJoinPool进行等价的处理， ForkJoinPool 可以将
		 * 10个任务才分成2个job来跑 ThreadPoolExecutor 可以将10个任务分成10个job来跑
		 * 算法上ForkJoinPool的确提升了性能，但是业务方面ThreadPoolExecutor才更加强大，没有优劣之分，环境不同，适用不同
		 */

	}

	/**
	 * <p>
	 * Title: peek
	 * </p>
	 * <p>
	 * Description: peek 对每个元素执行操作并返回一个新的 Stream
	 * 
	 * 普通的stream 就像 for() 一样，有序的进行操作，从打印顺序就可以看出来
	 * 
	 * parallel() 返回一个并行的等效流。可以返回本身，无论是因为流已经是并行的，或是因为底层的流状态被修改为并行。
	 * 这是一个intermediate operation。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月17日 下午4:56:47
	 */
	@Test
	public void peek() {

		/**
		 * 我们曾经了解过 parallelStream 当试用 parallel 可以将stream必为并行流，那么打印的结果是否仍然一致?
		 * Stream.of("one", "two", "three", "four").parallel()
		 * 
		 */
		Stream.of("one", "two", "three", "four").filter(e -> e.length() > 3)
				.peek(e -> System.out.println("Filtered value: " + e)).map(String::toUpperCase)
				.peek(e -> System.out.println("Mapped value: " + e)).collect(Collectors.toList());
	}

	/**
	 * <p>
	 * Title: findFirst
	 * </p>
	 * <p>
	 * Description: 这是一个 termimal 兼 short-circuiting 操作，它总是返回 Stream 的第一个元素，或者空。
	 * 
	 * 这里比较重点的是它的返回值类型：Optional。这也是一个模仿 Scala
	 * 语言中的概念，作为一个容器，它可能含有某值，或者不包含。使用它的目的是尽可能避免 NullPointerException。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月17日 下午5:06:48
	 */
	@Test
	public void findFirst() {

		Optional optional = Stream.of("one", "two", "three", "four").findFirst();
		System.out.println(optional);

	}

	/**
	 * <p>
	 * Title: optional
	 * </p>
	 * <p>
	 * Description: 在更复杂的 if (xx != null) 的情况中，使用 Optional
	 * 代码的可读性更好，而且它提供的是编译时检查，能极大的降低 NPE 这种 Runtime Exception
	 * 对程序的影响，或者迫使程序员更早的在编码阶段处理空值问题，而不是留到运行时再发现和调试。
	 * 
	 * Stream 中的 findAny、max/min、reduce 等方法等返回 Optional 值。还有例如
	 * IntStream.average() 返回 OptionalDouble 等等。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月17日 下午5:23:52
	 */
	public void optional() {

		class OptionalUtil {

			public void print(String text) {
				// Java 8
				Optional.ofNullable(text).ifPresent(System.out::println);
				// Pre-Java 8
				// if (text != null) {
				// System.out.println(text);
				// }
			}

			public int getLength(String text) {
				// Java 8
				return Optional.ofNullable(text).map(String::length).orElse(-1);
				// Pre-Java 8
				// return if (text != null) ? text.length() : -1;
			};

		}
		;

		OptionalUtil util = new OptionalUtil();
		String strA = " abcd ", strB = null;
		util.print(strA);
		util.print("");
		util.print(strB);
		System.out.println("=============================================");
		System.out.println(util.getLength(strA));
		System.out.println(util.getLength(""));
		System.out.println(util.getLength(strB));

	}

	/**
	 * <p>
	 * Title: reduce
	 * </p>
	 * <p>
	 * Description: TODO(describe the file)
	 * 
	 * 这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面
	 * Stream 的第一个、第二个、第 n 个元素组合。从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的
	 * reduce。例如 Stream 的 sum 就相当于 Integer sum = integers.reduce(0, (a, b) ->
	 * a+b); 或 Integer sum = integers.reduce(0, Integer::sum); 也有没有起始值的情况，这时会把
	 * Stream 的前面两个元素组合起来，返回的是 Optional。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月17日 下午5:38:26
	 */
	@Test
	public void reduce() {

		// 字符串连接，concat = "ABCD"
		String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
		logger.info("concat : {}", concat);
		// 求最小值，minValue = -3.0
//		double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
		double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double::min).get();
		logger.info("minValue : {}", minValue);
		// 求和，sumValue = 10, 有起始值
		int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
		logger.info("sumValue : {}", sumValue);
		// 求和，sumValue = 10, 无起始值
		sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
		logger.info("sumValue : {}", sumValue);
		// 过滤，字符串连接，concat = "ace"
		concat = Stream.of("a", "B", "c", "D", "e", "F").filter(x -> x.compareTo("Z") > 0).reduce(String::concat).get();
		logger.info("concat : {}", concat);

	}

	/**
	 * <p>
	 * Title: limit_skip
	 * </p>
	 * <p>
	 * Description: limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素（它是由一个叫 subStream
	 * 的方法改名而来）。
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月17日 下午5:51:20
	 */
	@Test
	public void limit_skip() {

		class Person {

			public int no;
			private String name;

			public Person(int no, String name) {
				this.no = no;
				this.name = name;
			}

			public String getName() {
				System.out.println(name);
				return name;
			}
		}

		List<Person> persons = new ArrayList<Person>();
		for (int i = 1; i <= 10000; i++) {
			Person person = new Person(i, "name" + i);
			persons.add(person);
		}

		List<String> personList2 = persons.stream().map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
		System.out.println(personList2);

		/**
		 * 这是一个有 10，000 个元素的 Stream，但在 short-circuiting 操作 limit 和 skip 的作用下，
		 * 管道中 map 操作指定的 getName() 方法的执行次数为 limit 所限定的 10 次，而最终返回结果在跳过前 3
		 * 个元素后只有后面 7 个返回。
		 */

	}

	/**
	 * <p>
	 * Title: sorted
	 * </p>
	 * <p>
	 * Description: sorted
	 * 
	 * 有一种情况是 limit/skip 无法达到 short-circuiting 目的的，就是把它们放在 Stream 的排序操作后， 原因跟
	 * sorted 这个 intermediate 操作有关：此时系统并不知道 Stream 排序后的次序如何，所以 sorted
	 * 中的操作看上去就像完全没有被 limit 或者 skip 一样。
	 * 
	 * 对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于你可以首先对 Stream 进行各类
	 * map、filter、limit、skip 甚至 distinct 来减少元素数量后， 再排序，这能帮助程序明显缩短执行时间。我们对清单 14
	 * 进行优化
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月17日 下午6:04:12
	 */
	@Test
	public void sorted() {

		class Person {

			public int no;
			private String name;

			public Person(int no, String name) {
				this.no = no;
				this.name = name;
			}

			public String getName() {
				System.out.println(name);
				return name;
			}
		}

		List<Person> persons = new ArrayList<Person>();
		for (int i = 1; i <= 5; i++) {
			Person person = new Person(i, "name" + i);
			persons.add(person);
		}
		List<Person> personList2 = persons.stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).limit(2)
				.collect(Collectors.toList());
		System.out.println(personList2);
		// personList2.forEach( c -> logger.info("{}",c.getName()));

		/**
		 * 即虽然最后的返回元素数量是 2，但整个管道中的 sorted 表达式执行次数没有像前面例子相应减少。 最后有一点需要注意的是，对一个
		 * parallel 的 Steam 管道来说，如果其元素是有序的，那么 limit 操作的成本会比较大， 因为它的返回对象必须是前 n
		 * 个也有一样次序的元素。取而代之的策略是取消元素间的次序，或者不要用 parallel Stream。
		 */
	}

	/**
	 * <p>
	 * Title: min_max_distinct
	 * </p>
	 * <p>
	 * Description: min 和 max 的功能也可以通过对 Stream 元素先排序，再 findFirst 来实现，但前者的性能会更好，为
	 * O(n)，而 sorted 的成本是 O(n log n)。 同时它们作为特殊的 reduce 方法被独立出来也是因为求最大最小值是很常见的操作。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月18日 上午9:37:57
	 */
	@Test
	public void min_max_distinct() {

		try {
			// 找出最大的一行
			BufferedReader br = new BufferedReader(new FileReader("d:\\a.txt"));
			int longest = br.lines().mapToInt(String::length).max().getAsInt();
			br.close();
			System.out.println("找出最大的一行=========================" + longest);

			// 找出全文的单词，转小写，并排序
			br = new BufferedReader(new FileReader("d:\\a.txt"));
			List<String> words = br.lines().peek(System.out::println).flatMap(line -> Stream.of(line.split(" ")))
					.filter(word -> word.length() > 0).map(String::toLowerCase).distinct().sorted()
					.collect(Collectors.toList());
			br.close();
			System.out.println("找出全文的单词，转小写，并排序=========================" + words);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <p>
	 * Title: match
	 * </p>
	 * <p>
	 * Description: Stream 有三个 match 方法，从语义上说： allMatch：Stream 中全部元素符合传入的
	 * predicate，返回 true anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
	 * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true 它们都不是要遍历全部元素才能返回结果。例如
	 * allMatch 只要一个元素不满足条件，就 skip 剩下的所有元素，返回 false。对清单 13 中的 Person 类稍做修改，加入一个
	 * age 属性和 getAge 方法。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月18日 上午9:48:47
	 */
	@Test
	public void match() {

		class Person {

			public int no;
			private String name;
			private int age;

			public Person(int no, String name, int age) {
				this.no = no;
				this.name = name;
				this.age = age;
			}

			public String getName() {
				System.out.println(name);
				return name;
			}

			public int getAge() {
				return age;
			}
		}

		List<Person> persons = new ArrayList();
		persons.add(new Person(1, "name" + 1, 10));
		persons.add(new Person(2, "name" + 2, 21));
		persons.add(new Person(3, "name" + 3, 34));
		persons.add(new Person(4, "name" + 4, 6));
		persons.add(new Person(5, "name" + 5, 55));
		boolean isAllAdult = persons.stream().allMatch(p -> p.getAge() > 18);
		System.out.println("All are adult? " + isAllAdult);
		boolean isThereAnyChild = persons.stream().anyMatch(p -> p.getAge() < 12);
		System.out.println("Any child? " + isThereAnyChild);

	}

	/**
	 * <p>
	 * Title: generate
	 * </p>
	 * <p>
	 * Description: Stream.generate
	 * 
	 * 通过实现 Supplier 接口，你可以自己来控制流的生成。这种情形通常用于随机数、常量的 Stream，或者需要前后元素间维持着某种状态信息的
	 * Stream。 把 Supplier 实例传递给 Stream.generate() 生成的 Stream，默认是串行（相对 parallel
	 * 而言）但无序的（相对 ordered 而言）。 由于它是无限的，在管道中，必须利用 limit 之类的操作限制 Stream 大小。
	 * 
	 * Stream.generate() 还接受自己实现的
	 * Supplier。例如在构造海量测试数据的时候，用某种自动的规则给每一个变量赋值；或者依据公式计算 Stream
	 * 的每个元素值。这些都是维持状态信息的情形。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月18日 上午10:00:54
	 */
	@Test
	public void generate() {

		class Person {

			public int no;
			private String name;
			private int age;

			public Person(int no, String name, int age) {
				this.no = no;
				this.name = name;
				this.age = age;
			}

			public String getName() {
				// System.out.println(name);
				return name;
			}

			public int getAge() {
				return age;
			}
		}

		class PersonSupplier implements Supplier<Person> {
			private int index = 0;
			private Random random = new Random();

			@Override
			public Person get() {
				return new Person(index++, "StormTestUser" + index, random.nextInt(100));
			}
		}

		Random seed = new Random();
		Supplier<Integer> random = seed::nextInt;
		Stream.generate(random).limit(10).forEach(System.out::println);
		// Another way
		IntStream.generate(() -> (int) (System.nanoTime() % 100)).limit(10).forEach(System.out::println);

		Stream.generate(new PersonSupplier()).limit(10)
				.forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));

	}

	/**
	 * <p>
	 * Title: iterate
	 * </p>
	 * <p>
	 * 
	 * Description: iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。
	 * 然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
	 * 
	 * 与 Stream.generate 相仿，在 iterate 时候管道必须有 limit 这样的操作来限制 Stream 大小。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月18日 下午2:09:16
	 */
	@Test
	public void iterate() {

		Stream.iterate(0, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));

	}

	/**
	 * <p>
	 * Title: groupingBy
	 * </p>
	 * <p>
	 * Description: 按照年龄归组
	 * 
	 * 用 Collectors 来进行 reduction 操作 java.util.stream.Collectors
	 * 类的主要作用就是辅助进行各类有用的 reduction 操作，例如转变输出为 Collection，把 Stream 元素进行归组。
	 * 
	 * </p>
	 * 
	 * @param
	 * @return void
	 * @throws @author
	 *             周顺宇
	 * @date 2018年9月18日 下午2:19:14
	 */
	@Test
	public void groupingBy() {

		class Person {

			public int no;
			private String name;
			private int age;

			public Person(int no, String name, int age) {
				this.no = no;
				this.name = name;
				this.age = age;
			}

			public String getName() {
//				System.out.println(name);
				return name;
			}

			public int getAge() {
				return age;
			}
		}

		class PersonSupplier implements Supplier<Person> {
			private int index = 0;
			private Random random = new Random();

			@Override
			public Person get() {
				return new Person(index++, "StormTestUser" + index, random.nextInt(100));
			}
		}
		
		Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).limit(100)
				.collect(Collectors.groupingBy(Person::getAge));
		Iterator it = personGroups.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
			System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
		}
		
	}
	
	
	/**
	 * <p>
	 * Title: Collectors_toMap
	 * </p>
	 * <p>
	 * Description: 延伸 Collectors.toMap
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月18日 下午3:33:45 
	 */
	@Test
	public void collectors_toMap(){
		
		class Person {
			public int no;
			private String name;
			private int age;
			public Person(int no, String name, int age) {
				this.no = no;
				this.name = name;
				this.age = age;
			}
			
			public int getNo() {
				return no;
			}
			public String getName() {
				return name;
			}
			public int getAge() {
				return age;
			}
		}
		
		class PersonSupplier implements Supplier<Person> {
			private int index = 0;
			private Random random = new Random();

			@Override
			public Person get() {
				return new Person(index++, "StormTestUser" + index, random.nextInt(10));
			}
		}
		
		Map<Integer, Person> mapp = Stream.generate(new PersonSupplier()).limit(10).collect(Collectors.toMap(Person::getNo, Function.identity()));
		
		System.out.println(mapp);
		
		System.out.println(mapp.get(1).getName());
		
		Map<Integer, String> map = Stream.generate(new PersonSupplier()).limit(10).collect(Collectors.toMap(Person::getNo, Person::getName));
 
		System.out.println(map);
		
	}
	
	/**
	 * <p>
	 * Title: partitioningBy
	 * </p>
	 * <p>
	 * Description: 按照未成年人和成年人归组
	 * 
	 * 在使用条件“年龄小于 18”进行分组后可以看到，不到 18 岁的未成年人是一组，成年人是另外一组。
	 * partitioningBy 其实是一种特殊的 groupingBy，它依照条件测试的是否两种结果来构造返回的数据结构，get(true) 和 get(false) 能即为全部的元素对象。
	 * 
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月18日 下午2:44:40 
	 */
	@Test
	public void partitioningBy(){
	
		class Person {

			public int no;
			private String name;
			private int age;

			public Person(int no, String name, int age) {
				this.no = no;
				this.name = name;
				this.age = age;
			}

			public String getName() {
				System.out.println(name);
				return name;
			}

			public int getAge() {
				return age;
			}
		}

		class PersonSupplier implements Supplier<Person> {
			private int index = 0;
			private Random random = new Random();

			@Override
			public Person get() {
				return new Person(index++, "StormTestUser" + index, random.nextInt(100));
			}
		}
		
		Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
		 limit(100).
		 collect(Collectors.partitioningBy(p -> p.getAge() < 18));
		System.out.println("Children number: " + children.get(true).size());
		System.out.println("Adult number: " + children.get(false).size());

		
	}
	
	
	/**
	 * 结束语
	 * 总之，Stream 的特性可以归纳为：
	 * 不是数据结构
	 * 它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、IO channel）抓取数据。
	 * 它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元素的新 Stream，而不是从 source 删除那些元素。
	 * 所有 Stream 的操作必须以 lambda 表达式为参数
	 * 不支持索引访问
	 * 你可以请求第一个元素，但无法请求第二个，第三个，或最后一个。不过请参阅下一项。
	 * 很容易生成数组或者 List
	 * 惰性化
	 * 很多 Stream 操作是向后延迟的，一直到它弄清楚了最后需要多少数据才会开始。
	 * Intermediate 操作永远是惰性化的。
	 * 并行能力
	 * 当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
	 * 可以是无限的
	 * 集合有固定大小，Stream 则不必。limit(n) 和 findFirst() 这类的 short-circuiting 操作可以对无限的 Stream 进行运算并很快完成。
	 */

}
