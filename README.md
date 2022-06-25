# Spicy-Von-Neumann-Fillet

This is a java simulation of a mini processor following the Von Neumann architecture.

>Computer System & Architecture Course Project @ GUC

## 1. Memory Architecture
### 1.1 Architecture : `Von Neumann`
- Von Neumann Architecture is a digital computer architecture whose design is based on the
    concept of stored program computers where program data and instruction data are stored
    in the same memory.

### 1.2 Memory Size: 32 * 2048

- The main memory addresses are from 0 to 2<sup>11</sup> − 1  (0 to 2047).
- Each memory block (row) contains 1 word which is 32 bits (4 bytes).
- The main memory is word addressable.
- Addresses from 0 to 1023 contain the program instructions.
- Addresses from 1024 to 2048 contain the data.

### 1.3 Registers: 33

- Size: $32$ bits
- 31 General-Purpose Registers (GPRS)
  - Names: R1 to R31
- 1 Zero Register
  - Name: R0
  - Hard-wired value “0” (cannot be overwritten by any instruction).
- 1 Program Counter
  - Name: PC

### 2 Instruction Set Architecture (ISA)
- Instruction Size: $32$ bits
- Instruction Types: $3$
  - R - Type
  - I - Type
  - J - Type
- Instructions

  |  Name | Mnemonic  | Type  | Format  |  Operation |    
  |---|---|---|---|---|
  |Add |ADD |R| ADD R1 R2 R3 |$R1 = R2 + R3$|
  |Subtract| SUB| R| SUB R1 R2 R3|$ R1 = R2 - R3$|
  |Multiply Immediate| MULI |I|MULI R1 R2 IMM| $R1 = R2 * IMM$|
  |Add Immediate| ADDI |I| ADDI R1 R2 IMM| R1 = R2 + IMM|
  |Branch if Not Equal |BNE |I| BNE R1 R2 IMM| $IF(R1 != R2)$ {$PC = PC+1+IMM $}|
  |And Immediate| ANDI| I| ANDI R1 R2 IMM |R1 = R2 & IMM|
  |Or Immediate| ORI |I| ORI R1 R2 IMM| R1 = R2 \| IMM|
  |Jump |J |J| J ADDRESS |\*PC = PC[31:28] \|\| ADDRESS|
  |Shift Left Logical $^∗$| SLL |R| SLL R1 R2 SHAMT| R1 = R2 << SHAMT
  |Shift Right Logical $^∗$| SRL |R| SRL R1 R2 SHAMT |R1 = R2 >>SHAMT
  |Load Word |LW |I| LW R1 R2 IMM |R1 = MEM[R2 + IMM]|
  |Store Word |SW |I| SW R1 R2 IMM| MEM[R2 + IMM] = R1|
\* \|\| is concatination, SLL and SRL: R3 will be 0 in the instruction format.

 

