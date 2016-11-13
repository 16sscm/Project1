/**
 * Created by zt02 on 2016/10/29.
 */

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 一个类中中一个方法，所以类注释即方法注释
 * 方法中完成了输出地图、移动、帮助、悔棋、取消悔棋、胜负判定等功能
 * 具体实现方法具体说明
 */
public class Jungle {
    public static void main(String[] args) throws FileNotFoundException {
            System.out.println("*帮助请输入help；重新开始输入restart；悔棋请输入undo；取消悔棋输入redo");
            System.out.println("输入数字加字母操作：");
            System.out.println("  1鼠、2猫、3狼、4狗、5豹、6虎、7狮、8象");
            System.out.println("  w向上、a向左、s向下、d向右");
            //读取文件，输出地图
            Scanner scanner1 = new Scanner(new File("tile.txt"));
            Scanner scanner2 = new Scanner(new File("animal.txt"));
            String[] map = {" 　 ", " 水 ", " 陷 ", " 家 ", " 陷 ", " 家 "};
            String[] animal1 = {" 　 ", "1鼠 ", "2猫 ", "3狼 ", "4狗 ", "5豹 ", "6虎 ", "7狮 ", "8象 "};
            String[] animal2 = {" 　 ", " 鼠1", " 猫2", " 狼3", " 狗4", " 豹5", " 虎6", " 狮7", " 象8"};
            String[] a1 = new String[9];
            String[] a2 = new String[9];
            String[][] element = new String[7][9];
            //第一层循环来初始化地图
            for (int m = 0; m < 7; m++) {
                String String1 = scanner1.next();
                String String2 = scanner2.next();
                for (int i = 0; i < 9; i++) {
                    char theChar1 = String1.charAt(i);
                    //将读取的字符转化为整数，要减去48
                    int char1 = (int) theChar1 - 48;
                    a1[i] = map[char1];
                }
                for (int j = 0; j < 9; j++) {
                    char theChar2 = String2.charAt(j);
                    //将读取的字符转化为整数，要减去48
                    int char2 = (int) theChar2 - 48;
                    //判断左右动物
                    if (j < 5) {
                        a2[j] = animal1[char2];
                    }
                    if (j >= 5) {
                        a2[j] = animal2[char2];
                    }
                }
                //第二层循环，按一定规则初始化地图
                for (int j = 0; j < 9; j++) {
                    if (!a1[j].equals(" 　 ")) {
                        element[m][j] = a1[j];
                    } else {
                        if (!a2[j].substring(1, 2).equals("　")) {
                            element[m][j] = a2[j];
                        } else element[m][j] = " 　 ";
                    }
                }
            }
            //输出初始地图
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(element[i][j]);
                }
                System.out.println();
            }
            System.out.println();
            //定义两个布尔变量player 、roll3，来实现玩家交换
            boolean player = true;
            boolean roll3 = true;
            //三维数组来做悔棋功能
            int p = 0;
            int q = 0;
            String[][][] boardHistory = new String[1000][7][9];
            Scanner input = new Scanner(System.in);
            //循环来实现行动和吃子，完成棋盘的变化
            while (true) {
                //将棋盘记录到三维数组中
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 9; j++) {
                        boardHistory[p][i][j] = element[i][j];
                    }
                }
                if (player == true) System.out.print("The left player,next move：");
                if (player == false) System.out.println("The right player,next move:");
                System.out.println();
                String choose = input.next();
                if (choose.length()>1) {
                    String object = choose.substring(0, 1);
                    String direction = choose.substring(1, 2);
                    //为了跳出两层for循环，用两个布尔变量控制
                    boolean roll1 = true;
                    boolean roll2 = true;
                    //取消悔棋
                    if (choose.equals("redo")) {
                        if (p>=q){
                            System.out.println("error,last position");
                            player = !player;
                            p--;
                        } else {
                            for (int i = 0; i < 7; i++) {
                                for (int j = 0; j < 9; j++) {
                                    element[i][j] = boardHistory[p + 1][i][j];
                                }
                            }
                        }
                    }
                    //悔棋
                    else if (choose.equals("undo")) {
                        if (p < 1) {
                            System.out.println("error,initial position");
                            player = !player;
                            p--;
                        }
                        if (p >= 1) {
                            for (int i = 0; i < 7; i++) {
                                for (int j = 0; j < 9; j++) {
                                    element[i][j] = boardHistory[p - 1][i][j];
                                }
                            }
                            p = p - 2;
                        }
                    }
                    //重新开始
                    else if (choose.equals("restart")) {
                        for (int i = 0; i < 7; i++) {
                            for (int j = 0; j < 9; j++) {
                                element[i][j] = boardHistory[0][i][j];
                                player = false;
                            }
                        }
                        p = 0 - 1;
                    } else if (choose.equals("help")) {
                        System.out.println("帮助:");
                        System.out.println("战斗力：象>狮>虎>豹>狗>狼>猫>鼠");
                        System.out.println("每只兽每次走一方格，除己方兽穴和小河以外，前后左右均可。");
                        System.out.println("特殊走法： ");
                        System.out.println("1.狮虎在小河边时，可以纵横对直跳过小河");
                        System.out.println("2.鼠是唯一可以走入小河的兽，陆地上兽不能吃小河中的鼠，小河中的鼠也不能吃陆地上的象");
                        System.out.println("斗兽棋吃法分普通吃法和特殊此法，普通吃法是按照兽的战斗力强弱，强者可以吃弱者。");
                        System.out.println("特殊吃法如下： ");
                        System.out.println("1、鼠吃象法：八兽的吃法除按照战斗力强弱次序外，惟鼠能吃象，象不能吃鼠。");
                        System.out.println("2、互吃法：凡同类相遇，可互相吃。");
                        System.out.println("3、陷阱：敌兽走入陷阱失去战斗力，本方的任意兽类都可以吃陷阱里的兽类。");
                        System.out.println("胜负判定： ");
                        System.out.println("1.任何一方的兽走入敌方的兽穴就算胜利（自己的兽类不可以走入自己的兽穴）；");
                        System.out.println("2.何一方的兽被吃光就算失败，对方获胜");
                        System.out.println("3.任何一方所有活着的兽被对方困住，均不可移动时，就算失败，对方获胜");
                        player = !player;
                    } else if (choose.equals("exit")) {
                        System.out.println("Thanks!");
                        break;
                    }
                    //判断输入的指令是否有意义
                    else {
                        if ((object.equals("1") || object.equals("2") || object.equals("3") || object.equals("4") || object.equals("5") || object.equals("6") || object.equals("7")
                                || object.equals("8")) && (direction.equals("w") || direction.equals("a") || direction.equals("s") || direction.equals("d"))) {
                            q++;
                            //player == true时左方玩家
                            if (player == true) {
                                //roll3 = !roll3是为了防止左方玩家错误选择后，player = !player进入第二个if块
                                roll3 = !roll3;
                                //定义x,来检查棋盘
                                int x = 0;
                                //双层循环匹配对象
                                for (int m = 0; m < 7; m++) {
                                    for (int n = 0; n < 9; n++) {
                                        String piece = element[m][n].substring(0, 1);
                                        if (object.equals(piece)) {
                                            //分对象进行不通操作，包括越界提醒，不同情况地图变化，错误提醒，由于不同对象不同方向情况基本相似，以一个为例
                                            if (object.equals("1")) {
                                                //借用一个中间变量来改变棋盘元素，利于交换
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    //越界提醒
                                                    if (m - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }//水处的特殊情况
                                                    else {
                                                        if ((m == 1 || m == 4) && (n == 3 || n == 4 || n == 5)) {
                                                            if (element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 鼠1")) {
                                                                element[m - 1][n] = "1鼠 ";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if ((m == 2 || m == 5) && (n == 3 || n == 4 || n == 5)) {
                                                            if (element[m - 1][n].equals(" 水 ") || element[m - 1][n].equals(" 鼠1")) {
                                                                element[m - 1][n] = "1鼠 ";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } //己方陷阱中,对方动物失去战斗力
                                                        else if (((m == 4 && n == 1) || (m == 5 && n == 0)) && element[m - 1][n].substring(0, 1).equals(" ")) {
                                                            element[m - 1][n] = "1鼠 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            //陷阱处的特殊情况
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 　 ")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m - 1][n] = mid;
                                                                } else if (element[m - 1][n].equals(" 象8")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m - 1][n] = "1鼠 ";
                                                                } else if (element[m - 1][n].equals(" 水 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m - 1][n].equals(" 鼠1")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 　 ")) {
                                                                    element[m][n] = element[m - 1][n];
                                                                    element[m - 1][n] = mid;
                                                                } else if (element[m - 1][n].equals(" 象8")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m - 1][n].equals(" 水 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m - 1][n].equals(" 家 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m - 1][n].equals(" 陷 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m - 1][n].equals(" 鼠1")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 1 || m == 2 || m == 4 || m == 5) && (n == 3)) {
                                                            if (element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 鼠1")) {
                                                                element[m][n - 1] = "1鼠 ";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if ((m == 1 || m == 2 || m == 4 || m == 5) && (n == 4 || n == 5)) {
                                                            if (element[m][n - 1].equals(" 水 ") || element[m][n - 1].equals(" 鼠1")) {
                                                                element[m][n - 1] = "1鼠 ";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if (((m == 3 && n == 2) || (m == 2 && n == 1) || (m == 4 && n == 1)) && element[m][n - 1].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "1鼠 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 1) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 　 ")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n - 1] = mid;
                                                                } else if (element[m][n - 1].equals(" 象8")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 水 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 家 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 陷 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 鼠1")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 　 ")) {
                                                                    element[m][n] = element[m][n - 1];
                                                                    element[m][n - 1] = mid;
                                                                } else if (element[m][n - 1].equals(" 象8")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n - 1].equals(" 水 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n - 1].equals(" 家 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 陷 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n - 1].equals(" 鼠1")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 2 || m == 5) && (n == 3 || n == 4 || n == 5)) {
                                                            if (element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 鼠1")) {
                                                                element[m + 1][n] = "1鼠 ";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if ((m == 1 || m == 4) && (n == 3 || n == 4 || n == 5)) {
                                                            if (element[m + 1][n].equals(" 水 ") || element[m + 1][n].equals(" 鼠1")) {
                                                                element[m + 1][n] = "1鼠 ";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if (((m == 2 && n == 1) || (m == 1 && n == 0)) && element[m + 1][n].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "1鼠 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 　 ")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m + 1][n] = mid;
                                                                } else if (element[m + 1][n].equals(" 象8")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 水 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 家 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 陷 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 鼠1")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 　 ")) {
                                                                    element[m][n] = element[m + 1][n];
                                                                    element[m + 1][n] = mid;
                                                                } else if (element[m + 1][n].equals(" 象8")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m + 1][n].equals(" 水 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m + 1][n].equals(" 家 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 陷 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m + 1][n].equals(" 鼠1")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n + 1 > 8) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 1 || m == 2 || m == 4 || m == 5) && (n == 5)) {
                                                            if (element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 鼠1")) {
                                                                element[m][n + 1] = "1鼠 ";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if ((m == 1 || m == 2 || m == 4 || m == 5) && (n == 3 || n == 4)) {
                                                            if (element[m][n + 1].equals(" 水 ") || element[m][n + 1].equals(" 鼠1")) {
                                                                element[m][n + 1] = "1鼠 ";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals(" 　 ")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n + 1] = mid;
                                                                } else if (element[m][n + 1].equals(" 象8")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 水 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 家 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 陷 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 鼠1")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals(" 　 ")) {
                                                                    element[m][n] = element[m][n + 1];
                                                                    element[m][n + 1] = mid;
                                                                } else if (element[m][n + 1].equals(" 象8")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n + 1].equals(" 水 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n + 1].equals(" 家 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 陷 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n + 1].equals(" 鼠1")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("6")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m <= 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 6 && n < 6 && n > 2) || (m == 3 && n < 6 && n > 2)) {
                                                            if (element[m - 1][n] == " 鼠1" || element[m - 2][n] == " 鼠1") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m - 3][n].equals(" 象8") || element[m - 3][n].equals(" 狮7") || !element[m - 3][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 3][n] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 4 && n == 1) || (m == 5 && n == 0)) && element[m - 1][n].substring(0, 1).equals(" ")) {
                                                            element[m - 1][n] = "6虎 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 象8") || element[m - 1][n].equals(" 狮7") || !element[m - 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m - 1][n] = mid;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 象8") || element[m - 1][n].equals(" 狮7") || !element[m - 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 1][n] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 0 && n < 6 && n > 2) || (m == 3 && n < 6 && n > 2)) {
                                                            if (element[m + 1][n] == " 鼠1" || element[m + 2][n] == " 鼠1") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m + 3][n].equals(" 象8") || element[m + 3][n].equals(" 狮7") || !element[m + 3][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m + 3][n] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 2 && n == 1) || (m == 1 && n == 0)) && element[m + 1][n].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "6虎 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 象8") || element[m + 1][n].equals(" 狮7") || !element[m + 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m + 1][n] = mid;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 象8") || element[m + 1][n].equals(" 狮7") || !element[m + 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m + 1][n] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n <= 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if (n == 6 && (m == 1 || m == 2 || m == 4 || m == 5)) {
                                                            if (element[m][n - 1] == " 鼠1" || element[m][n - 2] == " 鼠1" || element[m][n - 3] == " 鼠1") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m][n - 4].equals(" 象8") || element[m][n - 4].equals(" 狮7") || !element[m][n - 4].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n - 4] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 3 && n == 2) || (m == 2 && n == 1) || (m == 4 && n == 1)) && element[m][n - 1].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "6虎 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 1) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 象8") || element[m][n - 1].equals(" 狮7") || !element[m][n - 1].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n - 1] = mid;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 象8") || element[m][n - 1].equals(" 狮7") || !element[m][n - 1].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n - 1] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if (n == 2 && (m == 1 || m == 2 || m == 4 || m == 5)) {
                                                            if (element[m][n + 1] == " 鼠1" || element[m][n + 2] == " 鼠1" || element[m][n + 3] == " 鼠1") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m][n + 4].equals(" 象8") || element[m][n + 4].equals(" 狮7") || !element[m][n + 4].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n + 4] = mid;
                                                                }
                                                            }
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals(" 象8") || element[m][n + 1].equals(" 狮7") || !element[m][n + 1].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n + 1] = mid;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals(" 象8") || element[m][n + 1].equals(" 狮7") || !element[m][n + 1].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n + 1] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("7")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m <= 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 6 && n < 6 && n > 2) || (m == 3 && n < 6 && n > 2)) {
                                                            if (element[m - 1][n] == " 鼠1" || element[m - 2][n] == " 鼠1") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m - 3][n].equals(" 象8") || !element[m - 3][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 3][n] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 4 && n == 1) || (m == 5 && n == 0)) && element[m - 1][n].substring(0, 1).equals(" ")) {
                                                            element[m - 1][n] = "7狮 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 象8") || !element[m - 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m - 1][n] = mid;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 象8") || !element[m - 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 1][n] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 0 && n < 6 && n > 2) || (m == 3 && n < 6 && n > 2)) {
                                                            if (element[m + 1][n] == " 鼠1" || element[m + 2][n] == " 鼠1") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m + 3][n].equals(" 象8") || !element[m + 3][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m + 3][n] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 2 && n == 1) || (m == 1 && n == 0)) && element[m + 1][n].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "7狮 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 象8") || !element[m + 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m + 1][n] = mid;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 象8") || !element[m + 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m + 1][n] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n <= 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if (n == 6 && (m == 1 || m == 2 || m == 4 || m == 5)) {
                                                            if (element[m][n - 1] == " 鼠1" || element[m][n - 2] == " 鼠1" || element[m][n - 3] == " 鼠1") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m][n - 4].equals(" 象8") || !element[m][n - 4].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n - 4] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 3 && n == 2) || (m == 2 && n == 1) || (m == 4 && n == 1)) && element[m][n - 1].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "7狮 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 1) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 象8") || element[m][n - 1].equals(" 狮7")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n - 1] = mid;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 象8") || element[m][n - 1].equals(" 狮7")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n - 1] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if (n == 2 && (m == 1 || m == 2 || m == 4 || m == 5)) {
                                                            if (element[m][n + 1] == " 鼠1" || element[m][n + 2] == " 鼠1" || element[m][n + 3] == " 鼠1") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m][n + 4].equals(" 象8") || !element[m][n + 4].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n + 4] = mid;
                                                                }
                                                            }
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals(" 象8") || !element[m][n + 1].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n + 1] = mid;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals(" 象8") || !element[m][n + 1].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n + 1] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("8")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 1) || (m == 5 && n == 0)) && element[m - 1][n].substring(0, 1).equals(" ")) {
                                                            element[m - 1][n] = "8象 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 水 ") || !element[m - 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 水 ") || !element[m - 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 3 && n == 2) || (m == 2 && n == 1) || (m == 4 && n == 1)) && element[m][n - 1].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "8象 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 1) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 水 ") || !element[m][n - 1].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 水 ") || !element[m][n - 1].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 水 ") || !element[m][n + 1].substring(0, 1).equals(" ")) {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            }
                                                        } else {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 水 ") || !element[m][n + 1].substring(0, 1).equals(" ")) {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 1) || (m == 1 && n == 0)) && element[m + 1][n].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "8象 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 水 ") || !element[m + 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 水 ") || !element[m + 1][n].substring(0, 1).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("2")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 1) || (m == 5 && n == 0)) && element[m - 1][n].substring(0, 1).equals(" ")) {
                                                            element[m - 1][n] = "2猫 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 猫2")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 猫2")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 1) || (m == 1 && n == 0)) && element[m + 1][n].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "2猫 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 猫2")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 猫2")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 3 && n == 2) || (m == 2 && n == 1) || (m == 4 && n == 1)) && element[m][n - 1].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "2猫 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 1) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 猫2")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 猫2")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 猫2")) {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 猫2")) {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("3")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 1) || (m == 5 && n == 0)) && element[m - 1][n].substring(0, 1).equals(" ")) {
                                                            element[m - 1][n] = "3狼 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 猫2") || element[m - 1][n].equals(" 狼3")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 猫2") || element[m - 1][n].equals(" 狼3")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 1) || (m == 1 && n == 0)) && element[m + 1][n].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "3狼 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 猫2") || element[m + 1][n].equals(" 狼3")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 猫2") || element[m + 1][n].equals(" 狼3")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 3 && n == 2) || (m == 2 && n == 1) || (m == 4 && n == 1)) && element[m][n - 1].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "3狼 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 1) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 猫2") || element[m][n - 1].equals(" 狼3")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 猫2") || element[m][n - 1].equals(" 狼3")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 猫2") || element[m][n + 1].equals(" 狼3")) {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 猫2") || element[m][n + 1].equals(" 狼3")) {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("4")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 1) || (m == 5 && n == 0)) && element[m - 1][n].substring(0, 1).equals(" ")) {
                                                            element[m - 1][n] = "4狗 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 猫2")
                                                                        || element[m - 1][n].equals(" 狼3") || element[m - 1][n].equals(" 狗4")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 猫2")
                                                                        || element[m - 1][n].equals(" 狼3") || element[m - 1][n].equals(" 狗4")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 1) || (m == 1 && n == 0)) && element[m + 1][n].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "4狗 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 猫2")
                                                                        || element[m + 1][n].equals(" 狼3") || element[m + 1][n].equals(" 狗4")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 猫2")
                                                                        || element[m + 1][n].equals(" 狼3") || element[m + 1][n].equals(" 狗4")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 3 && n == 2) || (m == 2 && n == 1) || (m == 4 && n == 1)) && element[m][n - 1].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "4狗 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 1) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 猫2")
                                                                        || element[m][n - 1].equals(" 狼3") || element[m][n - 1].equals(" 狗4")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 猫2")
                                                                        || element[m][n - 1].equals(" 狼3") || element[m][n - 1].equals(" 狗4")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 猫2")
                                                                    || element[m][n + 1].equals(" 狼3") || element[m][n + 1].equals(" 狗4")) {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 猫2")
                                                                    || element[m][n + 1].equals(" 狼3") || element[m][n + 1].equals(" 狗4")) {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("5")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 1) || (m == 5 && n == 0)) && element[m - 1][n].substring(0, 1).equals(" ")) {
                                                            element[m - 1][n] = "5豹 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 猫2")
                                                                        || element[m - 1][n].equals(" 狼3") || element[m - 1][n].equals(" 狗4") || element[m - 1][n].equals(" 豹5")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 鼠1") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals(" 猫2")
                                                                        || element[m - 1][n].equals(" 狼3") || element[m - 1][n].equals(" 狗4") || element[m - 1][n].equals(" 豹5")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 1) || (m == 1 && n == 0)) && element[m + 1][n].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "5豹 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 0) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 猫2")
                                                                        || element[m + 1][n].equals(" 狼3") || element[m + 1][n].equals(" 狗4") || element[m + 1][n].equals(" 豹5")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 鼠1") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals(" 猫2")
                                                                        || element[m + 1][n].equals(" 狼3") || element[m + 1][n].equals(" 狗4") || element[m + 1][n].equals(" 豹5")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 3 && n == 2) || (m == 2 && n == 1) || (m == 4 && n == 1)) && element[m][n - 1].substring(0, 1).equals(" ")) {
                                                            element[m][n - 1] = "5豹 ";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 1) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 猫2")
                                                                        || element[m][n - 1].equals(" 狼3") || element[m][n - 1].equals(" 狗4") || element[m][n - 1].equals(" 豹5")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 鼠1") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals(" 猫2")
                                                                        || element[m][n - 1].equals(" 狼3") || element[m][n - 1].equals(" 狗4") || element[m][n - 1].equals(" 豹5")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 猫2")
                                                                    || element[m][n + 1].equals(" 狼3") || element[m][n + 1].equals(" 狗4") || element[m][n + 1].equals(" 豹5")) {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if (element[m][n + 1].equals(" 鼠1") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals(" 猫2")
                                                                    || element[m][n + 1].equals(" 狼3") || element[m][n + 1].equals(" 狗4") || element[m][n + 1].equals(" 豹5")) {
                                                                element[m][n + 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            roll1 = !roll1;
                                            //满足跳出两层循环
                                            break;
                                        } else x++;
                                    }
                                    if (roll1 == false)
                                        break;
                                }
                                //如果63个元素中没有操作对象，打印错误要求重新输入
                                if (x == 63) {
                                    System.out.println("error,move again");
                                    player = !player;
                                }
                            }//要求player == false且不执行第一个if，其他与第一个if类似，不做说明
                            if (player == false && roll3 == true) {
                                roll3 = !roll3;
                                int x = 0;
                                for (int m = 0; m < 7; m++) {
                                    for (int n = 0; n < 9; n++) {
                                        String piece = element[m][n].substring(2, 3);
                                        if (object.equals(piece)) {
                                            if (object.equals("1")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 1 || m == 4) && (n == 3 || n == 4 || n == 5)) {
                                                            if (element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("1鼠 ")) {
                                                                element[m - 1][n] = " 鼠1";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if ((m == 2 || m == 5) && (n == 3 || n == 4 || n == 5)) {
                                                            if (element[m - 1][n].equals(" 水 ") || element[m - 1][n].equals("1鼠 ")) {
                                                                element[m - 1][n] = " 鼠1";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if (((m == 4 && n == 7) || (m == 5 && n == 8)) && element[m - 1][n].substring(2, 3).equals(" ")) {
                                                            element[m - 1][n] = " 鼠1";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals(" 　 ")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m - 1][n] = mid;
                                                                } else if (element[m - 1][n].equals("8象 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m - 1][n].equals(" 水 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m - 1][n].equals(" 家 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m - 1][n].equals(" 陷 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m - 1][n].equals("1鼠 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals(" 　 ")) {
                                                                    element[m][n] = element[m - 1][n];
                                                                    element[m - 1][n] = mid;
                                                                } else if (element[m - 1][n].equals("8象 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m - 1][n].equals(" 水 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m - 1][n].equals(" 家 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m - 1][n].equals(" 陷 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m - 1][n].equals("1鼠 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n - 1 < 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 1 || m == 2 || m == 4 || m == 5) && (n == 3)) {
                                                            if (element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("1鼠 ")) {
                                                                element[m][n - 1] = " 鼠1";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if ((m == 1 || m == 2 || m == 4 || m == 5) && (n == 4 || n == 5)) {
                                                            if (element[m][n - 1].equals(" 水 ") || element[m][n - 1].equals("1鼠 ")) {
                                                                element[m][n - 1] = " 鼠1";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 　 ")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n - 1] = mid;
                                                                } else if (element[m][n - 1].equals("8象 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 水 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 家 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 陷 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals("1鼠 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 　 ")) {
                                                                    element[m][n] = element[m][n - 1];
                                                                    element[m][n - 1] = mid;
                                                                } else if (element[m][n - 1].equals("8象 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n - 1].equals(" 水 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n - 1].equals(" 家 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n - 1].equals(" 陷 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n - 1].equals("1鼠 ")) {
                                                                    element[m][n - 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 2 || m == 5) && (n == 3 || n == 4 || n == 5)) {
                                                            if (element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("1鼠 ")) {
                                                                element[m + 1][n] = " 鼠1";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if ((m == 1 || m == 4) && (n == 3 || n == 4 || n == 5)) {
                                                            if (element[m + 1][n].equals(" 水 ") || element[m + 1][n].equals("1鼠 ")) {
                                                                element[m + 1][n] = " 鼠1";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if (((m == 1 && n == 8) || (m == 2 && n == 7)) && element[m + 1][n].substring(2, 3).equals(" ")) {
                                                            element[m + 1][n] = " 鼠1";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals(" 　 ")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m + 1][n] = mid;
                                                                } else if (element[m + 1][n].equals("8象 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 水 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 家 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 陷 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals("1鼠 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals(" 　 ")) {
                                                                    element[m][n] = element[m + 1][n];
                                                                    element[m + 1][n] = mid;
                                                                } else if (element[m + 1][n].equals("8象 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m + 1][n].equals(" 水 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m + 1][n].equals(" 家 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m + 1][n].equals(" 陷 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m + 1][n].equals("1鼠 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n + 1 > 8) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 1 || m == 2 || m == 4 || m == 5) && (n == 5)) {
                                                            if (element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("1鼠 ")) {
                                                                element[m][n + 1] = " 鼠1";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if ((m == 1 || m == 2 || m == 4 || m == 5) && (n == 3 || n == 4)) {
                                                            if (element[m][n + 1].equals(" 水 ") || element[m][n + 1].equals("1鼠 ")) {
                                                                element[m][n + 1] = " 鼠1";
                                                                element[m][n] = " 水 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else if (((m == 2 && n == 7) || (m == 3 && n == 6) || (m == 4 && n == 7)) && element[m][n + 1].substring(2, 3).equals(" ")) {
                                                            element[m][n + 1] = " 鼠1";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 7) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals(" 　 ")) {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n + 1] = mid;
                                                                } else if (element[m][n + 1].equals("8象 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 水 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 家 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 陷 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals("1鼠 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals(" 　 ")) {
                                                                    element[m][n] = element[m][n + 1];
                                                                    element[m][n + 1] = mid;
                                                                } else if (element[m][n + 1].equals("8象 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n + 1].equals(" 水 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n + 1].equals(" 家 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else if (element[m][n + 1].equals(" 陷 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else if (element[m][n + 1].equals("1鼠 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("6")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m <= 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 6 && n < 6 && n > 2) || (m == 3 && n < 6 && n > 2)) {
                                                            if (element[m - 1][n] == "1鼠 " || element[m - 2][n] == "1鼠 ") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m - 3][n].equals("8象 ") || element[m - 3][n].equals("7狮 ") || !element[m - 3][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 3][n] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 4 && n == 7) || (m == 5 && n == 8)) && element[m - 1][n].substring(2, 3).equals(" ")) {
                                                            element[m - 1][n] = " 虎6";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals("8象 ") || element[m - 1][n].equals("7狮 ") || !element[m - 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m - 1][n] = mid;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals("8象 ") || element[m - 1][n].equals("7狮 ") || !element[m - 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 1][n] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 0 && n < 6 && n > 2) || (m == 3 && n < 6 && n > 2)) {
                                                            if (element[m + 1][n] == "1鼠 " || element[m + 2][n] == "1鼠 ") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m + 3][n].equals("8象 ") || element[m + 3][n].equals("7狮 ") || !element[m + 3][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 3][n] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 1 && n == 8) || (m == 2 && n == 7)) && element[m + 1][n].substring(2, 3).equals(" ")) {
                                                            element[m + 1][n] = " 虎6";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals("8象 ") || element[m + 1][n].equals("7狮 ") || !element[m + 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m + 1][n] = mid;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals("8象 ") || element[m + 1][n].equals("7狮 ") || !element[m + 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m + 1][n] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n <= 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if (n == 6 && (m == 1 || m == 2 || m == 4 || m == 5)) {
                                                            if (element[m][n - 1] == "1鼠 " || element[m][n - 2] == "1鼠 " || element[m][n - 3] == "1鼠 ") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m][n - 4].equals("8象 ") || element[m][n - 4].equals("7狮 ") || !element[m][n - 4].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n - 4] = mid;
                                                                }
                                                            }
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals("8象 ") || element[m][n - 1].equals("7狮 ") || !element[m][n - 1].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n - 1] = mid;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals("8象 ") || element[m][n - 1].equals("7狮 ") || !element[m][n - 1].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n - 1] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if (n == 2 && (m == 1 || m == 2 || m == 4 || m == 5)) {
                                                            if (element[m][n + 1] == "1鼠 " || element[m][n + 2] == "1鼠 " || element[m][n + 3] == "1鼠 ") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m][n + 4].equals("8象 ") || element[m][n + 4].equals("7狮 ") || !element[m][n + 4].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n + 4] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 2 && n == 7) || (m == 3 && n == 6) || (m == 4 && n == 7)) && element[m][n + 1].substring(2, 3).equals(" ")) {
                                                            element[m][n + 1] = " 虎6";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 7) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals("8象 ") || element[m][n + 1].equals("7狮 ") || !element[m][n + 1].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n + 1] = mid;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals("8象 ") || element[m][n + 1].equals("7狮 ") || !element[m][n + 1].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n + 1] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("7")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m <= 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 6 && n < 6 && n > 2) || (m == 3 && n < 6 && n > 2)) {
                                                            if (element[m - 1][n] == "1鼠 " || element[m - 2][n] == "1鼠 ") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m - 3][n].equals("8象 ") || !element[m - 3][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 3][n] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 4 && n == 7) || (m == 5 && n == 8)) && element[m - 1][n].substring(2, 3).equals(" ")) {
                                                            element[m - 1][n] = " 狮7";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals("8象 ") || !element[m - 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m - 1][n] = mid;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals("8象 ") || !element[m - 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m - 1][n] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if ((m == 0 && n < 6 && n > 2) || (m == 3 && n < 6 && n > 2)) {
                                                            if (element[m + 1][n] == "1鼠 " || element[m + 2][n] == "1鼠 ") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m + 3][n].equals("8象 ") || !element[m + 3][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m + 3][n] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 1 && n == 8) || (m == 2 && n == 7)) && element[m + 1][n].substring(2, 3).equals(" ")) {
                                                            element[m + 1][n] = " 狮7";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals("8象 ") || !element[m + 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m + 1][n] = mid;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals("8象 ") || !element[m + 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m + 1][n] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (n <= 0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if (n == 6 && (m == 1 || m == 2 || m == 4 || m == 5)) {
                                                            if (element[m][n - 1] == "1鼠 " || element[m][n - 2] == "1鼠 " || element[m][n - 3] == "1鼠 ") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m][n - 4].equals("8象 ") || !element[m][n - 4].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n - 4] = mid;
                                                                }
                                                            }
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n - 1].equals(" 象 ") || element[m][n - 1].equals(" 狮 ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n - 1] = mid;
                                                                }
                                                            } else {
                                                                if (element[m][n - 1].equals(" 象 ") || element[m][n - 1].equals(" 狮 ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n - 1] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    } else {
                                                        if (n == 2 && (m == 1 || m == 2 || m == 4 || m == 5)) {
                                                            if (element[m][n + 1] == "1鼠 " || element[m][n + 2] == "1鼠 " || element[m][n + 3] == "1鼠 ") {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if (element[m][n + 4].equals("8象 ") || !element[m][n + 4].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n + 4] = mid;
                                                                }
                                                            }
                                                        } else if (((m == 2 && n == 7) || (m == 3 && n == 6) || (m == 4 && n == 7)) && element[m][n + 1].substring(2, 3).equals(" ")) {
                                                            element[m][n + 1] = " 狮7";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 7) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals("8象 ") || !element[m][n + 1].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 陷 ";
                                                                    element[m][n + 1] = mid;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals("8象 ") || !element[m][n + 1].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n] = " 　 ";
                                                                    element[m][n + 1] = mid;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("8")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                    if (direction.equals("w")) {
                                                        if (m<=0) {
                                                            System.out.println("Sorry,out of boundary!");
                                                            player = !player;
                                                        } else {
                                                            if (((m == 4 && n == 7) || (m == 5 && n == 8)) && element[m - 1][n].substring(2, 3).equals(" ")) {
                                                                element[m - 1][n] = " 象8";
                                                                element[m][n] = " 　 ";
                                                            } else if (m == 4 && n == 8) {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                    if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 水 ") || !element[m - 1][n].substring(2, 3).equals(" ")) {
                                                                        System.out.println("error,move again");
                                                                        player = !player;
                                                                    } else {
                                                                        element[m - 1][n] = mid;
                                                                        element[m][n] = " 陷 ";
                                                                    }
                                                                } else {
                                                                    if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 水 ") || !element[m - 1][n].substring(2, 3).equals(" ")) {
                                                                        System.out.println("error,move again");
                                                                        player = !player;
                                                                    } else {
                                                                        element[m - 1][n] = mid;
                                                                        element[m][n] = " 　 ";
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                if (direction.equals("a")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 水 ") || !element[m][n - 1].substring(2, 3).equals(" ")) {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            }
                                                        } else {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 水 ") || !element[m][n - 1].substring(2, 3).equals(" ")) {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            } else {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 7) || (m == 3 && n == 6) || (m == 4 && n == 7)) && element[m][n + 1].substring(2, 3).equals(" ")) {
                                                            element[m][n + 1] = " 象8";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 7) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 水 ") || !element[m][n + 1].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 水 ") || !element[m][n + 1].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 1 && n == 8) || (m == 2 && n == 7)) && element[m + 1][n].substring(2, 3).equals(" ")) {
                                                            element[m + 1][n] = " 象8";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 水 ") || !element[m + 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 水 ") || !element[m + 1][n].substring(2, 3).equals(" ")) {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                } else {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("2")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 7) || (m == 5 && n == 8)) && element[m - 1][n].substring(2, 3).equals(" ")) {
                                                            element[m - 1][n] = " 猫2";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("2猫 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("2猫 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 1 && n == 8) || (m == 2 && n == 7)) && element[m + 1][n].substring(2, 3).equals(" ")) {
                                                            element[m + 1][n] = " 猫2";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("2猫 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("2猫 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("2猫 ")) {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("2猫 ")) {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 7) || (m == 3 && n == 6) || (m == 4 && n == 7)) && element[m][n + 1].substring(2, 3).equals(" ")) {
                                                            element[m][n + 1] = " 猫2";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 7) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("2猫 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("2猫 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("3")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 7) || (m == 5 && n == 8)) && element[m - 1][n].substring(2, 3).equals(" ")) {
                                                            element[m - 1][n] = " 狼3";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("2猫 ") || element[m - 1][n].equals("3狼 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("2猫 ") || element[m - 1][n].equals("3狼 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 1 && n == 8) || (m == 2 && n == 7)) && element[m + 1][n].substring(2, 3).equals(" ")) {
                                                            element[m + 1][n] = " 狼3";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("2猫 ") || element[m + 1][n].equals("3狼 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("2猫 ") || element[m + 1][n].equals("3狼 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("2猫 ") || element[m][n - 1].equals("3狼 ")) {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("2猫 ") || element[m][n - 1].equals("3狼 ")) {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 7) || (m == 3 && n == 6) || (m == 4 && n == 7)) && element[m][n + 1].substring(2, 3).equals(" ")) {
                                                            element[m][n + 1] = " 狼3";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 7) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("2猫 ") || element[m][n + 1].equals("3狼 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("2猫 ") || element[m][n + 1].equals("3狼 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("4")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 7) || (m == 5 && n == 8)) && element[m - 1][n].substring(2, 3).equals(" ")) {
                                                            element[m - 1][n] = " 狗4";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("2猫 ")
                                                                        || element[m - 1][n].equals("3狼 ") || element[m - 1][n].equals("4狗 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("2猫 ")
                                                                        || element[m - 1][n].equals("3狼 ") || element[m - 1][n].equals("4狗 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 1 && n == 8) || (m == 2 && n == 7)) && element[m + 1][n].substring(2, 3).equals(" ")) {
                                                            element[m + 1][n] = " 狗4";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("2猫 ")
                                                                        || element[m + 1][n].equals("3狼 ") || element[m + 1][n].equals("4狗 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("2猫 ")
                                                                        || element[m + 1][n].equals("3狼 ") || element[m + 1][n].equals("4狗 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("2猫 ")
                                                                    || element[m][n - 1].equals("3狼 ") || element[m][n - 1].equals("4狗 ")) {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("2猫 ")
                                                                    || element[m][n - 1].equals("3狼 ") || element[m][n - 1].equals("4狗 ")) {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 7) || (m == 3 && n == 6) || (m == 4 && n == 7)) && element[m][n + 1].substring(2, 3).equals(" ")) {
                                                            element[m][n + 1] = " 狗4";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 7) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("2猫 ")
                                                                        || element[m][n + 1].equals("3狼 ") || element[m][n + 1].equals("4狗 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("2猫 ")
                                                                        || element[m][n + 1].equals("3狼 ") || element[m][n + 1].equals("4狗 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (object.equals("5")) {
                                                String mid = null;
                                                mid = element[m][n];
                                                if (direction.equals("w")) {
                                                    if (m<=0) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 4 && n == 7) || (m == 5 && n == 8)) && element[m - 1][n].substring(2, 3).equals(" ")) {
                                                            element[m - 1][n] = " 豹5";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 4 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("2猫 ")
                                                                        || element[m - 1][n].equals("3狼 ") || element[m - 1][n].equals("4狗 ") || element[m - 1][n].equals("5豹 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m - 1][n].equals("1鼠 ") || element[m - 1][n].equals(" 　 ") || element[m - 1][n].equals("2猫 ")
                                                                        || element[m - 1][n].equals("3狼 ") || element[m - 1][n].equals("4狗 ") || element[m - 1][n].equals("5豹 ")) {
                                                                    element[m - 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("s")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 1 && n == 8) || (m == 2 && n == 7)) && element[m + 1][n].substring(2, 3).equals(" ")) {
                                                            element[m + 1][n] = " 豹5";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 2 && n == 8) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("2猫 ")
                                                                        || element[m + 1][n].equals("3狼 ") || element[m + 1][n].equals("4狗 ") || element[m + 1][n].equals("5豹 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m + 1][n].equals("1鼠 ") || element[m + 1][n].equals(" 　 ") || element[m + 1][n].equals("2猫 ")
                                                                        || element[m + 1][n].equals("3狼 ") || element[m + 1][n].equals("4狗 ") || element[m + 1][n].equals("5豹 ")) {
                                                                    element[m + 1][n] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("a")) {
                                                    if (m >= 6) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("2猫 ")
                                                                    || element[m][n - 1].equals("3狼 ") || element[m][n - 1].equals("4狗 ") || element[m][n - 1].equals("5豹 ")) {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 陷 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        } else {
                                                            if (element[m][n - 1].equals("1鼠 ") || element[m][n - 1].equals(" 　 ") || element[m][n - 1].equals("2猫 ")
                                                                    || element[m][n - 1].equals("3狼 ") || element[m][n - 1].equals("4狗 ") || element[m][n - 1].equals("5豹 ")) {
                                                                element[m][n - 1] = mid;
                                                                element[m][n] = " 　 ";
                                                            } else {
                                                                System.out.println("error,move again");
                                                                player = !player;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (direction.equals("d")) {
                                                    if (n > 7) {
                                                        System.out.println("Sorry,out of boundary!");
                                                        player = !player;
                                                    }else {
                                                        if (((m == 2 && n == 7) || (m == 3 && n == 6) || (m == 4 && n == 7)) && element[m][n + 1].substring(2, 3).equals(" ")) {
                                                            element[m][n + 1] = " 豹5";
                                                            element[m][n] = " 　 ";
                                                        } else if (m == 3 && n == 7) {
                                                            System.out.println("error,move again");
                                                            player = !player;
                                                        } else {
                                                            if ((m == 2 && (n == 0 || n == 8)) || (m == 3 && (n == 1 || n == 7)) || (m == 4 && (n == 0 || n == 8))) {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("2猫 ")
                                                                        || element[m][n + 1].equals("3狼 ") || element[m][n + 1].equals("4狗 ") || element[m][n + 1].equals("5豹 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 陷 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            } else {
                                                                if (element[m][n + 1].equals("1鼠 ") || element[m][n + 1].equals(" 　 ") || element[m][n + 1].equals("2猫 ")
                                                                        || element[m][n + 1].equals("3狼 ") || element[m][n + 1].equals("4狗 ") || element[m][n + 1].equals("5豹 ")) {
                                                                    element[m][n + 1] = mid;
                                                                    element[m][n] = " 　 ";
                                                                } else {
                                                                    System.out.println("error,move again");
                                                                    player = !player;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            roll2 = !roll2;
                                            break;
                                        } else x++;
                                    }
                                    if (roll2 == false) break;
                                }//在玩家输入正确但不存在的的操作对象时打印出错误提示
                                if (x == 63) {
                                    System.out.println("error,move again");
                                    player = !player;
                                }
                            }
                        } else {
                            System.out.println("error,move again");
                            player = !player;
                        }
                    }
                    //打印变化后的棋盘
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            System.out.print(element[i][j]);
                        }
                        System.out.println();
                    }
                    System.out.println();
                    //player和roll3同时取反，控制玩家交替
                    player = !player;
                    roll3 = !roll3;
                    //如果一方家被入侵，则失败，游戏结束
                    if (element[3][0] != " 家 ") {
                        System.out.println("Congratulations，right player win!");
                        break;
                    }
                    if (element[3][8] != " 家 ") {
                        System.out.println("Congratulations ，left player win!");
                        break;
                    }
                    int sum1 = 0;
                    int sum2 = 0;
                    //循环来检查地图中是否有一方被吃光，有则有一方胜利
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (!element[i][j].substring(0, 1).equals(" ")) sum1++;
                            if (!element[i][j].substring(2, 3).equals(" ")) sum2++;
                        }
                    }
                    if (sum1 == 0) {
                        System.out.println("Congratulations,right player win!");
                        break;
                    }
                    if (sum2 == 0) {
                        System.out.println("Congratulations,left player win!");
                        break;
                    }
                    //p的自增实现悔棋和取消悔棋等功能
                    p++;
                }
                else{
                    System.out.println("error,move again");
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            System.out.print(element[i][j]);
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            }
    }
}
