const validNum = (num) => {
  const numStr = String(num);
  let isValid = false;
  for (let idx = 1; idx < numStr.length; idx++) {
    if (numStr[idx] < numStr[idx - 1]) return false;
    numStr[idx] === numStr[idx - 1] && (isValid = true);
  }
  return isValid;
};

const part1 = () => {
  let count = 0;
  for (let num = 245318; num <= 765747; num++) {
    validNum(num) && count++;
  }
  console.log("Part 1:", count);
};

const validNum2 = (num) => {
  const numStr = String(num);
  let isValid = false;

  for(let idx = 1; idx < numStr.length; idx++) {
    const currChar = numStr[idx];
    if (currChar < numStr[idx - 1]) return false;
    if (currChar === numStr[idx - 1] && currChar !== numStr[idx - 2] && currChar !== numStr[idx+1]) {
      isValid = true;
    }
  }
  return isValid;
}

const part2 = () => {
  let count = 0;
  for (let num = 245318; num <= 765747; num++) {
    if(validNum2(num)){
      console.log(num);
      count++;
    }
  }
  console.log("Part 2:", count);
};

part2();
