package main

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strconv"
	"strings"
)

type Entry struct {
	num1     int
	num2     int
	char     string
	password string
}

func get_data() []Entry {
	file, _ := os.Open("input")
	scanner := bufio.NewScanner(file)
	var entry Entry
	var match []string
	data := []Entry{}
	re := regexp.MustCompile("([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)")
	for scanner.Scan() {
		match = re.FindStringSubmatch(scanner.Text())[1:5]
		entry.num1, _ = strconv.Atoi(match[0])
		entry.num2, _ = strconv.Atoi(match[1])
		entry.char = match[2]
		entry.password = match[3]
		data = append(data, entry)
	}
	return data
}

func part1(data []Entry) int {
	valids := 0
	for _, entry := range data {
		count := strings.Count(entry.password, entry.char)
		if count >= entry.num1 && count <= entry.num2 {
			valids++
		}
	}
	return valids
}

func part2(data []Entry) int {
	valids := 0
	for _, entry := range data {
        char1 := string(entry.password[entry.num1-1])
        char2 := string(entry.password[entry.num2-1])
		if (char1 == entry.char) != (char2 == entry.char) {
			valids++
		}
	}
	return valids
}

func main() {
	data := get_data()
	fmt.Printf("Part 1: %d\n", part1(data))
	fmt.Printf("Part 2: %d\n", part2(data))
}
