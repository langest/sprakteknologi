package main

import (
	"bufio"
	"fmt"
	"os"
)

var (
	neighbours map[string][]string
)

func main() {
	initNeighbours()
	lines, err := readLines("toEncrypt.txt")
	if err != nil {
		fmt.Println(err)
		return
	}

	//var encLines []string
	for _, line := range lines {
		for _, c := range line {
			if c == ' ' {
				fmt.Println(c)
			} else {
				fmt.Println(neighbours[fmt.Sprintf("%c", c)])
			}
		}
	}

}

func initNeighbours() {
	neighbours = make(map[string][]string)
	neighbours["a"] = []string{"q", "w", "s", "x", "z"}
	neighbours["b"] = []string{"v", "g", "h", "n"}
	neighbours["c"] = []string{"x", "f", "d", "v"}
	neighbours["d"] = []string{"e", "r", "f", "c", "x", "x", "s"}
	neighbours["e"] = []string{"w", "s", "d", "f", "r"}
	neighbours["f"] = []string{"r", "d", "c", "v", "g", "t"}
	neighbours["g"] = []string{"t", "y", "h", "b", "v", "f"}
	neighbours["h"] = []string{"y", "u", "j", "n", "b", "g"}
	neighbours["i"] = []string{"u", "j", "k", "l", "o"}
	neighbours["j"] = []string{"u", "i", "k", "m", "n", "h"}
	neighbours["k"] = []string{"i", "o", "l", "m", "j"}
	neighbours["m"] = []string{"n", "j", "k"}
	neighbours["n"] = []string{"b", "h", "j", "m"}
	neighbours["o"] = []string{"i", "k", "l", "p"}
	neighbours["p"] = []string{"o", "l", "ö", "ä", "å"}
	neighbours["q"] = []string{"a", "s", "w"}
	neighbours["r"] = []string{"e", "d", "f", "g", "t"}
	neighbours["s"] = []string{"t"}
	neighbours["t"] = []string{"r"}
	neighbours["u"] = []string{"p"}
	neighbours["v"] = []string{"g"}
	neighbours["w"] = []string{"v"}
	neighbours["x"] = []string{"y"}
	neighbours["y"] = []string{"z"}
	neighbours["å"] = []string{"ö", "ä"}
	neighbours["ä"] = []string{"ö", "å"}
	neighbours["ö"] = []string{"ä", "å"}
}

func readLines(path string) ([]string, error) {
	file, err := os.Open(path)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var lines []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}
	return lines, scanner.Err()
}

func writeLines(lines []string, path string) error {
	file, err := os.Create(path)
	if err != nil {
		return err
	}
	defer file.Close()

	w := bufio.NewWriter(file)
	for _, line := range lines {
		fmt.Fprintln(w, line)
	}
	return w.Flush()
}
