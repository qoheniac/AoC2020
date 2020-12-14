package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func get_data() []int {
	file, _ := os.Open("input")
	scanner := bufio.NewScanner(file)
	var data []int
	var num int
	for scanner.Scan() {
		num, _ = strconv.Atoi(scanner.Text())
		data = append(data, num)
	}
	return data
}

func part1(data []int) int {
	for i, a := range data[:len(data)-1] {
		for _, b := range data[i+1:] {
			if a+b == 2020 {
				return a * b
			}
		}
	}
	return 0
}

func part2(data []int) int {
	N := len(data)
	for i, a := range data[:N-2] {
		for j, b := range data[i+1 : N-1] {
			for _, c := range data[j+1:] {
				if a+b+c == 2020 {
					return a * b * c
				}
			}
		}
	}
	return 0
}

func main() {
	data := get_data()
	fmt.Printf("Part 1: %d\n", part1(data))
	fmt.Printf("Part 2: %d\n", part2(data))
}
