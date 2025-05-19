package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	program, err := loadProgram("programIP.txt")
	if err != nil {
		fmt.Println("Error loading program:", err)
		return
	}

	input := 5
	runIntcode(program, input)
}


func loadProgram(filename string) ([]int, error) {
	file, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var program []int
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		tokens := strings.Split(line, ",")
		for _, token := range tokens {
			val, err := strconv.Atoi(strings.TrimSpace(token))
			if err != nil {
				return nil, fmt.Errorf("invalid number in input: %v", err)
			}
			program = append(program, val)
		}
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return program, nil
}

func runIntcode(memory []int, input int) {
	ip := 0 

	get := func(offset int, mode int) int {
		if mode == 0 {
			return memory[memory[ip+offset]]
		}
		return memory[ip+offset]
	}

	for {
		opcode := memory[ip] % 100
		mode1 := (memory[ip] / 100) % 10
		mode2 := (memory[ip] / 1000) % 10

		switch opcode {
		case 1: 
			a := get(1, mode1)
			b := get(2, mode2)
			dest := memory[ip+3]
			memory[dest] = a + b
			ip += 4
		case 2: 
			a := get(1, mode1)
			b := get(2, mode2)
			dest := memory[ip+3]
			memory[dest] = a * b
			ip += 4
		case 3: 
			dest := memory[ip+1]
			memory[dest] = input
			ip += 2
		case 4: 
			val := get(1, mode1)
			fmt.Println("Output:", val)
			ip += 2
		case 5: 
			if get(1, mode1) != 0 {
				ip = get(2, mode2)
			} else {
				ip += 3
			}
		case 6: 
			if get(1, mode1) == 0 {
				ip = get(2, mode2)
			} else {
				ip += 3
			}
		case 7: 
			a := get(1, mode1)
			b := get(2, mode2)
			dest := memory[ip+3]
			if a < b {
				memory[dest] = 1
			} else {
				memory[dest] = 0
			}
			ip += 4
		case 8: 
			a := get(1, mode1)
			b := get(2, mode2)
			dest := memory[ip+3]
			if a == b {
				memory[dest] = 1
			} else {
				memory[dest] = 0
			}
			ip += 4
		case 99: 
			return
		default:
			panic(fmt.Sprintf("Unknown opcode %d at position %d", opcode, ip))
		}
	}
}
