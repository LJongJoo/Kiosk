package kiosk;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class KioskMain extends JFrame {
    private static final String CSV_FILE_PATH = "C:\\Users\\J J\\Desktop\\yalco\\receipt.csv";
    DefaultTableModel tableModel;
    int lineCount = 0;//패널이 몇개 생성되어야 할 지 개수를 세주는 변수
    IPhone[] deviceIPhone;
    IPad[] deviceIPad;
    Galaxy[] deviceGalaxy;
    GalaxyPad[] deviceGalaxyPad;

    Container c ;
    JPanel iphonePanel, ipadPanel, galaxyPanel, galaxyPadPanel,otherPanel;
    JButton iphoneButton, ipadButton, galaxyButton, galaxyPadButton,otherButton;
    Font font = new Font("맑은 고딕", Font.BOLD, 20);
    String[] fileName = {"IPhone","IPad","Galaxy","GalaxyPad","Other"};
    String filePath = "C:\\Users\\J J\\Desktop\\yalco\\java-prac\\image\\";

    JRadioButton[] storage = new JRadioButton[3];
    JRadioButton[] color = new JRadioButton[6];
    int storagePrice=0;
    //int totalPrice=0;
    private int totalSum=0;
    JLabel totalSumLabel;
    //int idx;
    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    KioskMain(){
        setTitle("스마트폰 자동 판매 키오스크");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c =getContentPane();
        c.setBackground(new Color(135,206,235));//배경색을 하늘색으로 설정
        c.setLayout(null); //컨테이너 레이아웃을 null로 설정

        //메뉴 패널 생성-----------------------------------------------------------------
        JPanel menuPanal = new JPanel();
        menuPanal.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        menuPanal.setBounds(20,15,900,100);//메뉴 버튼 위치 설정
        menuPanal.setBackground(new Color(135,206,235));
        iphoneButton = new JButton("iPhone");
        ipadButton = new JButton("iPad");
        galaxyButton = new JButton("Galaxy");
        galaxyPadButton = new JButton("GalaxyPad");
        otherButton = new JButton("Other");
        // 버튼의 크기 조절
        Dimension menuButtonSize = new Dimension(150, 40);
        iphoneButton.setPreferredSize(menuButtonSize);
        ipadButton.setPreferredSize(menuButtonSize);
        galaxyButton.setPreferredSize(menuButtonSize);
        galaxyPadButton.setPreferredSize(menuButtonSize);
        otherButton.setPreferredSize(menuButtonSize);

        //메뉴 버튼 폰트 설정
        iphoneButton.setFont(font);
        ipadButton.setFont(font);
        galaxyButton.setFont(font);
        galaxyPadButton.setFont(font);
        otherButton.setFont(font);

        //패널에 버튼부착
        menuPanal.add(iphoneButton);
        menuPanal.add(ipadButton);
        menuPanal.add(galaxyButton);
        menuPanal.add(galaxyPadButton);
        menuPanal.add(otherButton);
        //컨테이너에 패널 부착
        c.add(menuPanal);

        //--------------------------------------------------------------------------
        //iphone버튼을 눌렀을 경우
        iphonePanel =new JPanel();
        iphonePanel.setLayout(new GridLayout(2,5,10,10));
        iphonePanel.setBounds(50,120,900,500);
        iphonePanel.setBackground(new Color(135,206,235));
        nameFileRead(fileName[0],IPhone.class);
        //아이폰 이미지 및 라벨 붙이기
        for(int i =0; i<lineCount;i++) {
            imagePricePanel(deviceIPhone[i],fileName[0]);
        }

        c.add(iphonePanel);
        //---------------------------------------------------------------------------
        //--------------------------------------------------------------------------
        //ipad버튼을 눌렀을 경우
        ipadPanel =new JPanel();
        ipadPanel.setLayout(new GridLayout(2,5,10,10));
        ipadPanel.setBounds(50,120,890,500);
        ipadPanel.setBackground(new Color(135,206,235));
        nameFileRead(fileName[1],IPad.class);
        //아이패드 이미지 및 라벨 붙이기
        for(int i =0; i<lineCount;i++) {

            imagePricePanel(deviceIPad[i],fileName[1]);
        }

        c.add(ipadPanel);

        //---------------------------------------------------------------------------
        //--------------------------------------------------------------------------
        //Galaxy버튼을 눌렀을 경우
        galaxyPanel =new JPanel();
        galaxyPanel.setLayout(new GridLayout(2,5,10,10));
        galaxyPanel.setBounds(50,120,890,500);
        galaxyPanel.setBackground(new Color(135,206,235));
        nameFileRead(fileName[2],Galaxy.class);
        //갤럭시 이미지 및 라벨 붙이기
        for(int i =0; i<lineCount;i++) {

            imagePricePanel(deviceGalaxy[i],fileName[2]);
        }
        c.add(galaxyPanel);

        //---------------------------------------------------------------------------
        //--------------------------------------------------------------------------
        //galaxyPad버튼을 눌렀을 경우
        galaxyPadPanel =new JPanel();
        galaxyPadPanel.setLayout(new GridLayout(2,5,10,10));
        galaxyPadPanel.setBounds(50,120,890,500);
        galaxyPadPanel.setBackground(new Color(135,206,235));
        nameFileRead(fileName[3],GalaxyPad.class);
        //아이패드 이미지 및 라벨 붙이기
        for(int i =0; i<lineCount;i++) {

            imagePricePanel(deviceGalaxyPad[i],fileName[3]);
        }
        c.add(galaxyPadPanel);
        //---------------------------------------------------------------------------otherPanel
        otherPanel =new JPanel();
        otherPanel.setBounds(50,120,890,500);
        otherPanel.setLayout(null);
        otherPanel.setBackground(new Color(135,206,235));
        c.add(otherPanel);

        JLabel textFieldLabel = new JLabel("TextField");
        JTextField textField = new JTextField();
        textField.setBounds(300, 50, 200, 30);  // 위치와 크기 설정
        textFieldLabel.setBounds(200, 50, 200, 30);
        otherPanel.add(textField);
        otherPanel.add(textFieldLabel);


        JLabel spinnerModelLabel = new JLabel("SpinnerModel");
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);  // 초기값, 최소값, 최대값, 증가값
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setBounds(300, 100, 80, 30);  // 위치와 크기 설정
        spinnerModelLabel.setBounds(200, 100, 80, 30);
        otherPanel.add(spinner);
        otherPanel.add(spinnerModelLabel);

        JProgressBar progressBar = new JProgressBar();
        JLabel progressBarLabel = new JLabel("ProgressBar");
        progressBarLabel.setBounds(200, 150, 200, 20);
        progressBar.setBounds(300, 150, 200, 20);  // 위치와 크기 설정
        progressBar.setMinimum(0);  // 최소값 설정
        progressBar.setMaximum(100);  // 최대값 설정
        progressBar.setValue(30);  // 현재 값 설정
        otherPanel.add(progressBar);
        otherPanel.add(progressBarLabel);

        JButton startButton = new JButton("Start Progress");
        startButton.setBounds(520, 150, 250, 20);
        otherPanel.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 시작 버튼을 눌렀을 때 프로그래스바가 0에서 100까지 채워지도록 하는 코드
                Timer timer = new Timer(100, new ActionListener() {
                    int value = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        value++;
                        progressBar.setValue(value);

                        if (value == 100) {
                            ((Timer) e.getSource()).stop();
                            JOptionPane.showMessageDialog(otherPanel, "Progress Completed");
                        }
                    }
                });
                timer.start();
            }
        });

        JButton showDialogButton = new JButton("Show Dialog");
        showDialogButton.setBounds(300, 200, 150, 30);
        otherPanel.add(showDialogButton);

        // JDialog 생성
        JLabel dialogLabel = new JLabel("Dialog");
        dialogLabel.setBounds(200, 200, 150, 30);
        otherPanel.add(dialogLabel);
        JDialog dialog = new JDialog();
        dialog.setTitle("Dialog Example");
        dialog.setSize(300, 200);

        // JDialog에 추가할 컴포넌트나 내용을 설정

        // 버튼 클릭 시 JDialog를 보이도록 설정
        showDialogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
            }
        });


        //---------------------------------------------------------------------------

        //---------------------------------------------------------------------------
        //선택 내용 , 가격 보여주는 패널
        JPanel pricePanal = new JPanel();
        pricePanal.setLayout(new FlowLayout());
        pricePanal.setBounds(970,10,400,440);//메뉴 버튼 위치 설정 ,(좌,우)(위,아래),(넓이),(높이)
        pricePanal.setBackground(new Color(0,206,235));


        c.add(pricePanal);
        //---------------------------------------------------------------------------
        //가격 표만들기 talbe
        String[] columnNames = {"상품명","용량","색상","개수","가격","선택"};


        // 선택된 상품 정보를 담을 데이터 모델
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        DefaultTableCellRenderer dcr = new DefaultTableCellRenderer()//행의 마지막에 체크표시를 넣기위한 셀렌더러
        {
            public Component getTableCellRendererComponent  // 셀렌더러
            (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JCheckBox box= new JCheckBox();
                box.setSelected(((Boolean)value).booleanValue());
                box.setHorizontalAlignment(JLabel.CENTER);
                return box;
            }
        };

        //체크박스 추가
        table.getColumn("선택").setCellRenderer(dcr);
        JCheckBox box = new JCheckBox();
        box.setHorizontalAlignment(JLabel.CENTER);
        table.getColumn("선택").setCellEditor(new DefaultCellEditor(box));


        // 테이블이 들어갈 스크롤 패널 생성
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setPreferredSize(new Dimension(350, 350));
        // pricePanal에 스크롤 패널 추가
        pricePanal.add(scrollPane);

        //--------------------------------------------------------------------------- 메인 프레임 가격합
        JPanel totalSumPanal = new JPanel();
        totalSumPanal.setLayout(new GridLayout(2,2));
        totalSumPanal.setBounds(970,400,400,240);//메뉴 버튼 위치 설정 ,(좌,우)(위,아래),(넓이),(높이)
        totalSumPanal.setBackground(new Color(0,206,235));

        totalSumLabel = new JLabel("총 가격: 0원");
        totalSumLabel.setHorizontalAlignment(SwingConstants.CENTER); // JLabel을 가운데 정렬
        totalSumLabel.setVerticalAlignment(SwingConstants.CENTER); // JLabel을 세로로 가운데 정렬
        totalSumLabel.setFont(font);
        totalSumPanal.add(totalSumLabel);

        //----------------------------------------------------------------------테이블 삭제를 위한 코드

        //결제 및 취소버튼
        JPanel payDeletePanel = new JPanel();
        payDeletePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        payDeletePanel.setBounds(20,15,800,100);//메뉴 버튼 위치 설정
        payDeletePanel.setBackground(new Color(0,206,235));


        JButton payButton = new JButton("결제");
        payButton.setBackground(new Color(246,64,95));
        JButton allDeleteButton = new JButton("취소");
        JButton checkDeleteButton = new JButton("삭제");
        JButton plusButton = new JButton("+");
        JButton minusButton = new JButton("-");

        Dimension payDeleteButtonSize = new Dimension(100, 40);
        payButton.setPreferredSize(payDeleteButtonSize);
        allDeleteButton.setPreferredSize(payDeleteButtonSize);
        checkDeleteButton.setPreferredSize(payDeleteButtonSize);
        Dimension plusMinus = new Dimension(50, 40);
        plusButton.setPreferredSize(plusMinus);
        minusButton.setPreferredSize(plusMinus);

        plusButton.setFont(font);
        minusButton.setFont(font);
        payButton.setFont(font);
        allDeleteButton.setFont(font);
        checkDeleteButton.setFont(font);

        payDeletePanel.add(payButton);
        payDeletePanel.add(allDeleteButton);
        payDeletePanel.add(checkDeleteButton);
        payDeletePanel.add(plusButton);
        payDeletePanel.add(minusButton);

        totalSumPanal.add(payDeletePanel);
        c.add(totalSumPanal);
        // plusButton의 ActionListener에서 체크된 행의 개수 및 가격 증가 로직 추가
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 행의 인덱스를 저장할 리스트
                ArrayList<Integer> selectedRows = new ArrayList<>();

                // 테이블의 체크박스를 확인하고 선택된 행의 인덱스를 리스트에 추가
                for (int i = 0; i < table.getRowCount(); i++) {
                    Boolean isChecked = (Boolean) table.getValueAt(i, table.getColumn("선택").getModelIndex());
                    if (isChecked) {
                        selectedRows.add(i);
                    }
                }

                // 선택된 행의 개수가 0보다 크면 개수 및 가격 증가 로직 실행
                if (!selectedRows.isEmpty()) {
                    for (int rowIndex : selectedRows) {
                        // 현재 개수 및 가격 가져오기
                        int currentCount = (int) table.getValueAt(rowIndex, tableModel.findColumn("개수"));
                        int currentPrice = (int) table.getValueAt(rowIndex, tableModel.findColumn("가격"));

                        // 현재 선택된 행의 storage 가격으로 설정
                        storagePrice = currentPrice/currentCount;
                        // 개수 및 가격 증가
                        currentCount++;
                        currentPrice += storagePrice;

                        if (currentCount == 0) {		//개수가 0이 되면 테이블에서 데이터 삭제
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow != -1) {
                                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                                tableModel.removeRow(selectedRow);
                            }
                        } else {
                            table.setValueAt(currentCount, rowIndex, tableModel.findColumn("개수"));
                        }
                        // 개수 및 가격 업데이트

                        table.setValueAt(currentPrice, rowIndex, tableModel.findColumn("가격"));

                        // 총 가격 업데이트
                        totalSum += storagePrice;
                        totalSumLabel.setText("총 가격: " + decimalFormat.format(totalSum) + " 원");
                    }
                } else {
                    // 체크된 행이 없다면 메시지 출력
                    JOptionPane.showMessageDialog(KioskMain.this, "선택된 항목이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // minusButton의 ActionListener에서 체크된 행의 개수 및 가격 증가 로직 추가
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 행의 인덱스를 저장할 리스트
                ArrayList<Integer> selectedRows = new ArrayList<>();

                // 테이블의 체크박스를 확인하고 선택된 행의 인덱스를 리스트에 추가
                for (int i = 0; i < table.getRowCount(); i++) {
                    Boolean isChecked = (Boolean) table.getValueAt(i, table.getColumn("선택").getModelIndex());
                    if (isChecked) {
                        selectedRows.add(i);
                    }
                }

                // 선택된 행의 개수가 0보다 크면 개수 및 가격 증가 로직 실행
                if (!selectedRows.isEmpty()) {
                    for (int rowIndex : selectedRows) {
                        // 현재 개수 및 가격 가져오기
                        int currentCount = (int) table.getValueAt(rowIndex, tableModel.findColumn("개수"));
                        // 현재 선택된 행의 storage 가격으로 설정

                        if (currentCount > 0) {
                            int currentPrice = (int) table.getValueAt(rowIndex, tableModel.findColumn("가격"));
                            storagePrice = currentPrice/currentCount;

                            // 개수 및 가격 증가
                            currentCount--;
                            currentPrice -= storagePrice;

                            if (currentCount == 0) {
                                // 현재 개수가 0이면 행 삭제
                                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                                tableModel.removeRow(rowIndex);
                            } else {
                                // 그렇지 않으면 개수 및 가격 업데이트
                                table.setValueAt(currentCount, rowIndex, tableModel.findColumn("개수"));
                                table.setValueAt(currentPrice, rowIndex, tableModel.findColumn("가격"));
                            }

                            // 총 가격 업데이트
                            totalSum -= storagePrice;
                            totalSumLabel.setText("총 가격: " + decimalFormat.format(totalSum) + " 원");
                        }
                    }
                } else {
                    // 체크된 행이 없다면 메시지 출력
                    JOptionPane.showMessageDialog(KioskMain.this, "선택된 항목이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //결제 버튼을 누를때
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReceiptFrame();
                tableModel.setRowCount(0);
                totalSum=0;
                totalSumLabel.setText("총 가격: " + decimalFormat.format(totalSum) + " 원");
            }
        });

        // 삭제 버튼에 대한 ActionListener 추가
        checkDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 선택된 행들을 저장할 리스트
                ArrayList<Integer> selectedRows = new ArrayList<>();

                // 테이블의 체크박스를 확인하고 선택된 행을 리스트에 추가
                for (int i = 0; i < table.getRowCount(); i++) {
                    Boolean isChecked = (Boolean) table.getValueAt(i, table.getColumn("선택").getModelIndex());
                    if (isChecked) {
                        selectedRows.add(i);
                    }
                }

                // 리스트에 추가된 행들을 테이블에서 제거
                for (int i = selectedRows.size() - 1; i >= 0; i--) {
                    int rowIndex = selectedRows.get(i);
                    int priceToRemove = (int) table.getValueAt(rowIndex, 4); // 삭제할 행의 가격
                    tableModel.removeRow(rowIndex); // 테이블에서 행 제거
                    // 가격을 음수로 만들지 않도록 예외 처리
                    totalSum -= Math.min(priceToRemove, totalSum);
                }
                // 모든 행을 순회하면서 선택되지 않은 행의 가격을 합산하여 totalSum 갱신
                int rowCount = tableModel.getRowCount();
                totalSum = 0;
                for (int i = 0; i < rowCount; i++) {
                    if (!(Boolean) tableModel.getValueAt(i, tableModel.findColumn("선택"))) {
                        totalSum += (int) tableModel.getValueAt(i, tableModel.findColumn("가격"));
                    }
                }

                // 총 가격 업데이트
                totalSumLabel.setText("총 가격: " + decimalFormat.format(totalSum) + " 원");
            }
        });

        // 취소 버튼에 대한 ActionListener 추가
        allDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 테이블의 내용을 모두 삭제
                tableModel.setRowCount(0);

                // 총 가격 업데이트 (취소 후 가격은 0으로 설정)
                totalSumLabel.setText("총 가격: 0 원");
            }
        });

        //---------------------------------------------------------------------------

        //--------------------------------------------------------------------------- 동국대 로고 패널
        JPanel donggukPanel = new JPanel();
        donggukPanel.setLayout(new FlowLayout());
        donggukPanel.setBounds(50,620,900,130);//메뉴 버튼 위치 설정
        donggukPanel.setBackground(new Color(225,113,0));

        ImageIcon donggukImage = resizeImage(new ImageIcon(filePath+"dongguk.png"),400,120);
        JLabel donggukLabel= new JLabel(donggukImage);
        donggukPanel.add(donggukLabel);
        c.add(donggukPanel);
        //---------------------------------------------------------------------------
        //---------------------------------------------------------------------------

        setSize(1400,800);
        setVisible(true);

        // iPhonePanel을 보이도록 설정
        showPanel(iphonePanel);
        // 처음에 iphone버튼이 선택되도록 설정
        changeButtonColor(iphoneButton);

        //---------------------------------------------------------------------------
        //메뉴 버튼이 눌렸을 경우 해당 패널을 보여주는 ActionListener
        ActionListener buttonActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();

                if (clickedButton == iphoneButton) {
                    showPanel(iphonePanel);
                    changeButtonColor(iphoneButton);
                } else if (clickedButton == ipadButton) {
                    showPanel(ipadPanel);
                    changeButtonColor(ipadButton);
                } else if (clickedButton == galaxyButton) {
                    showPanel(galaxyPanel);
                    changeButtonColor(galaxyButton);
                } else if (clickedButton == galaxyPadButton) {
                    showPanel(galaxyPadPanel);
                    changeButtonColor(galaxyPadButton);
                } else if (clickedButton == otherButton) {
                    showPanel(otherPanel);
                    changeButtonColor(otherButton);
                }
            }
        };
        iphoneButton.addActionListener(buttonActionListener);
        ipadButton.addActionListener(buttonActionListener);
        galaxyButton.addActionListener(buttonActionListener);
        galaxyPadButton.addActionListener(buttonActionListener);
        otherButton.addActionListener(buttonActionListener);
    }
    //---------------------------------------------------------------------------


    public static void main(String[] args) {
        new KioskMain();

    }

    // 해당 패널만 보이게 하는 메소드
    void showPanel(JPanel panel) {
        iphonePanel.setVisible(false);
        ipadPanel.setVisible(false);
        galaxyPanel.setVisible(false);
        galaxyPadPanel.setVisible(false);
        otherPanel.setVisible(false);

        panel.setVisible(true);
    }

    // 버튼의 색상을 변경하는 메소드
    void changeButtonColor(JButton button) {
        // 모든 버튼의 색상을 원래대로 변경
        iphoneButton.setBackground(null);
        ipadButton.setBackground(null);
        galaxyButton.setBackground(null);
        galaxyPadButton.setBackground(null);
        otherButton.setBackground(null);
        // 선택된 버튼의 색상을 변경
        button.setBackground(new Color(179,253,192));
    }

    void nameFileRead(String fileName, Class<?> deviceClass) {
        String line;
        lineCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath + fileName + ".csv"))) {
            br.readLine();
            while (br.readLine() != null) {
                lineCount++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath + fileName + ".csv"))) {
            br.readLine();
            int index = 0;
            // 파일에서 읽어올 데이터를 담을 배열 동적으로 생성
            if (deviceClass == IPhone.class) {
                deviceIPhone = new IPhone[lineCount];
            } else if (deviceClass == IPad.class) {
                deviceIPad = new IPad[lineCount];
            }else if (deviceClass == Galaxy.class) {
                deviceGalaxy = new Galaxy[lineCount];
            }else if (deviceClass == GalaxyPad.class) {
                deviceGalaxyPad = new GalaxyPad[lineCount];
            }

            while ((line = br.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(line, ",");

                String name = st.nextToken();
                String[] colors = new String[6];
                String[] storages = new String[3];
                int[] prices = new int[3];

                // Parsing colors
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = st.nextToken();
                }

                // Parsing storages
                for (int i = 0; i < storages.length; i++) {
                    storages[i] = st.nextToken();
                }

                // Parsing prices
                for (int i = 0; i < prices.length; i++) {
                    prices[i] = Integer.parseInt(st.nextToken());
                }

                // Create object based on deviceClass
                if (deviceClass == IPhone.class) {
                    deviceIPhone[index] = new IPhone(name, prices, colors, storages, index);
                } else if (deviceClass == IPad.class) {
                    deviceIPad[index] = new IPad(name, prices, colors, storages, index);
                }else if (deviceClass == Galaxy.class) {
                    deviceGalaxy[index] = new Galaxy(name, prices, colors, storages, index);
                }else if (deviceClass == GalaxyPad.class) {
                    deviceGalaxyPad[index] = new GalaxyPad(name, prices, colors, storages, index);
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //사진,가격용 패널 생성 메소드
    void imagePricePanel(Device panelInfo, String fileName) {
        // JButton에 ImageIcon과 JLabel을 추가할 FlowLayout으로 설정
        JButton button = new JButton();
        button.setLayout(new FlowLayout());
        // 이미지를 버튼에 추가
        ImageIcon imageIcon = new ImageIcon(filePath + fileName + "\\" + panelInfo.getName() + ".png");
        JLabel imageLabel = new JLabel(new ImageIcon(resizeImage(imageIcon, 250, 250).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH)));
        button.add(imageLabel);

        // 라벨을 버튼에 추가
        JLabel nameLabel = new JLabel(panelInfo.getName());
        button.add(nameLabel);
        // ActionListener를 추가하여 새로운 프레임을 열도록 설정
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewFrame(panelInfo.getName(), panelInfo.getIndex(), panelInfo.getPriceOptions(),panelInfo.getItem());
            }
        });

        //메뉴 버튼 폰트 설정
        nameLabel.setFont(font);
        // 버튼을 iphonePanel에 추가

        // 패널을 가져와서 버튼을 해당 패널에 추가
        if(panelInfo.getItem()==1111) {
            iphonePanel.add(button);
        }else if(panelInfo.getItem()==2222) {
            ipadPanel.add(button);
        }else if(panelInfo.getItem()==3333) {
            galaxyPanel.add(button);
        }else if(panelInfo.getItem()==4444) {
            galaxyPadPanel.add(button);
        }

    }

    //제품 세부 옵션을 선택하기위해 새로운 프레임 생성
    void openNewFrame(String deviceName,int idn,int[] price,int Item) {

        // 새로운 프레임 생성
        JFrame newFrame = new JFrame(deviceName + " 상세 정보");
        newFrame.setLayout(new GridLayout(8,1));

        //-------------------------------------------------------------용량
        // 용량에 관한 라디오버튼 추가
        JLabel storageLabel = new JLabel("용량 선택");
        storageLabel.setHorizontalAlignment(SwingConstants.CENTER); // JLabel을 가운데 정렬
        storageLabel.setVerticalAlignment(SwingConstants.CENTER); // JLabel을 세로로 가운데 정렬
        storageLabel.setFont(font); // 폰트 설정

        // GridLayout을 사용하여 가운데 정렬
        JPanel storageCenterPanel = new JPanel(new GridLayout(1, 1));
        storageCenterPanel.add(storageLabel);
        newFrame.add(storageCenterPanel);

        //용량의 라디오 버튼 생성
        JPanel storageRadioPanel = new JPanel();
        storageRadioPanel.setLayout(new FlowLayout());
        ButtonGroup storageButtonGroup = new ButtonGroup(); // 라디오 버튼을 그룹으로 묶음
        newFrame.add(storageRadioPanel);
        //-------------------------------------------------------------

        //-------------------------------------------------------------색상
        JLabel colorLabel = new JLabel("색상 선택");
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER); // JLabel을 가운데 정렬
        colorLabel.setVerticalAlignment(SwingConstants.CENTER); // JLabel을 세로로 가운데 정렬
        colorLabel.setFont(font); // 폰트 설정
        JPanel ColorCenterPanel = new JPanel(new GridLayout(1, 1));
        ColorCenterPanel.add(colorLabel);
        newFrame.add(ColorCenterPanel);

        JPanel colorRadioPanel = new JPanel();
        colorRadioPanel.setLayout(new FlowLayout());
        ButtonGroup colorButtonGroup = new ButtonGroup(); // 라디오 버튼을 그룹으로 묶음
        newFrame.add(colorRadioPanel);
        //-------------------------------------------------------------

        //-------------------------------------------------------------개수
        JLabel countNumLabel = new JLabel("개수 선택");
        countNumLabel.setHorizontalAlignment(SwingConstants.CENTER); // JLabel을 가운데 정렬
        countNumLabel.setVerticalAlignment(SwingConstants.CENTER); // JLabel을 세로로 가운데 정렬
        countNumLabel.setFont(font); // 폰트 설정

        JPanel countNumCenterPanel = new JPanel(new GridLayout(2, 1));


        JComboBox<Integer> strCombo = new JComboBox<>();
        for (int i = 1; i <= 100; i++) {
            strCombo.addItem(i);
        }

        countNumCenterPanel.add(countNumLabel);
        countNumCenterPanel.add(strCombo);// 콤보박스를 프레임에 추가
        newFrame.add(countNumCenterPanel);

        //-------------------------------------------------------------
        //-------------------------------------------------------------가격의 합
        //가격의 총합을 적어주는 레이블 생성
        JLabel sumLabel = new JLabel("현재 0 원 입니다");
        sumLabel.setHorizontalAlignment(SwingConstants.CENTER); // JLabel을 가운데 정렬
        sumLabel.setVerticalAlignment(SwingConstants.CENTER); // JLabel을 세로로 가운데 정렬
        sumLabel.setFont(font); // 폰트 설정

        JPanel sumCenterPanel = new JPanel(new GridLayout(1, 1));
        sumCenterPanel.add(sumLabel);
        newFrame.add(sumCenterPanel);

        //-------------------------------------------------------------
        //-------------------------------------------------------------확인 버튼
        JButton selecButton = new JButton("확인");
        //확인 버튼을 눌렀을 경우 ActionListener
        selecButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 내용 가져오기
                String selectedStorage = getSelectedButtonText(storageButtonGroup);
                String selectedColor = getSelectedButtonText(colorButtonGroup);
                int selectedCount = (Integer) strCombo.getSelectedItem();
                int totalPrice = storagePrice * selectedCount;

                // 용량 또는 색상이 선택되지 않았을 경우 오류 메시지 출력
                if (selectedStorage == null || selectedColor == null) {
                    JOptionPane.showMessageDialog(newFrame, "옵션을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    return; // 선택되지 않았으면 더 이상 진행하지 않고 함수 종료
                }

                // 데이터 모델에 행 추가(table에 데이터 추가하기)
                updateExistingRow(deviceName, selectedStorage, selectedColor, selectedCount, totalPrice);
                // 테이블 가격 업데이트
                int rowCount = tableModel.getRowCount();
                totalSum = 0;

                //총 가격을 더해주는 for문
                for (int i = 0; i < rowCount; i++) {
                    int price = (int) tableModel.getValueAt(i, 4); // 4는 가격 column의 인덱스
                    totalSum += price;
                }

                // 총 가격 업데이트
                totalSumLabel.setText("총 가격: " + decimalFormat.format(totalSum) + " 원");

                // 확인 버튼을 누르면 프레임이 닫힘
                newFrame.dispose();
            }
        });

        newFrame.add(selecButton);
        //-------------------------------------------------------------


        //라디오 버튼이 선택 되었을 때 가격을 더해주는 ActionListener
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean storageSelected = false; // ^^ 선택 여부를 나타내는 플래그

                // 라디오 버튼에 따라 가격 재설정
                for (int i = 0; i < storage.length; i++) {
                    if (storage[i].isSelected()) {
                        storagePrice = price[i]; // 선택한 라디오 버튼에 따라 가격 재설정
                        storageSelected = true; // ^^ 라디오 버튼이 선택되었음을 표시
                        break;
                    }
                }
                int selectedCount = (Integer) strCombo.getSelectedItem();
                int totalPrice = storagePrice * selectedCount;

                //개수와 가격을 곱해서 총가격 업데이트
                if (storageSelected) { // ^^ 라디오 버튼이 선택된 경우에만 계산
                    totalPrice = storagePrice * selectedCount;
                } else {
                    totalPrice = 0; // 라디오 버튼이 선택되지 않은 경우 0원으로 설정
                }

                sumLabel.setText("현재 " + decimalFormat.format(totalPrice) + " 원 입니다");
            }
        };

        // 콤보박스의 내용이 변경될 때 합계 가격이 수정되도록 코드 추가
        strCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    // 선택된 개수와 용량에 따라 가격 계산
                    int selectedCount = (Integer) strCombo.getSelectedItem();
                    int totalPrice = storagePrice * selectedCount;

                    sumLabel.setText("현재 " + decimalFormat.format(totalPrice) + " 원 입니다");
                }
            }
        });

        //item값에 따라 따라 라디오 버튼을 생성해 줌
        if(Item==1111) {
            //용량라디오버튼을 생성하고 컨테이너에 add해주는 for문
            for (int j = 0; j < 3; j++) {
                storage[j] = new JRadioButton(deviceIPhone[idn].storageOptions[j]);
                storage[j].addActionListener(listener);
                storageButtonGroup.add(storage[j]); // 라디오 버튼을 그룹에 추가
                // x라는 글자가 포함되어 있다면 해당 라디오버튼을 선택할 수 없도록 설정
                if (deviceIPhone[idn].storageOptions[j].contains("x")) {
                    storage[j].setEnabled(false);
                }
                storageRadioPanel.add(storage[j]);

            }
            for (int j = 0; j < 6; j++) {
                color[j] = new JRadioButton(deviceIPhone[idn].colorOptions[j]);
                color[j].addActionListener(listener);
                colorButtonGroup.add(color[j]); // 라디오 버튼을 그룹에 추가
                // x라는 글자가 포함되어 있다면 해당 라디오버튼을 선택할 수 없도록 설정
                if (deviceIPhone[idn].colorOptions[j].contains("x")) {
                    color[j].setEnabled(false);
                }
                colorRadioPanel.add(color[j]);
            }
        }else if(Item==2222) {
            //용량라디오버튼을 생성하고 컨테이너에 add해주는 for문
            for (int j = 0; j < 3; j++) {
                storage[j] = new JRadioButton(deviceIPad[idn].storageOptions[j]);
                storage[j].addActionListener(listener);
                storageButtonGroup.add(storage[j]); // 라디오 버튼을 그룹에 추가
                // x라는 글자가 포함되어 있다면 해당 라디오버튼을 선택할 수 없도록 설정
                if (deviceIPad[idn].storageOptions[j].contains("x")) {
                    storage[j].setEnabled(false);
                }
                storageRadioPanel.add(storage[j]);

            }
            for (int j = 0; j < 6; j++) {
                color[j] = new JRadioButton(deviceIPad[idn].colorOptions[j]);
                color[j].addActionListener(listener);
                colorButtonGroup.add(color[j]); // 라디오 버튼을 그룹에 추가
                // x라는 글자가 포함되어 있다면 해당 라디오버튼을 선택할 수 없도록 설정
                if (deviceIPad[idn].colorOptions[j].contains("x")) {
                    color[j].setEnabled(false);
                }
                colorRadioPanel.add(color[j]);
            }
        }else if(Item==3333) {
            //용량라디오버튼을 생성하고 컨테이너에 add해주는 for문
            for (int j = 0; j < 3; j++) {
                storage[j] = new JRadioButton(deviceGalaxy[idn].storageOptions[j]);
                storage[j].addActionListener(listener);
                storageButtonGroup.add(storage[j]); // 라디오 버튼을 그룹에 추가
                // x라는 글자가 포함되어 있다면 해당 라디오버튼을 선택할 수 없도록 설정
                if (deviceGalaxy[idn].storageOptions[j].contains("x")) {
                    storage[j].setEnabled(false);
                }
                storageRadioPanel.add(storage[j]);

            }
            for (int j = 0; j < 6; j++) {
                color[j] = new JRadioButton(deviceGalaxy[idn].colorOptions[j]);
                color[j].addActionListener(listener);
                colorButtonGroup.add(color[j]); // 라디오 버튼을 그룹에 추가
                // x라는 글자가 포함되어 있다면 해당 라디오버튼을 선택할 수 없도록 설정
                if (deviceGalaxy[idn].colorOptions[j].contains("x")) {
                    color[j].setEnabled(false);
                }
                colorRadioPanel.add(color[j]);
            }
        }else if(Item==4444) {
            //용량라디오버튼을 생성하고 컨테이너에 add해주는 for문
            for (int j = 0; j < 3; j++) {
                storage[j] = new JRadioButton(deviceGalaxyPad[idn].storageOptions[j]);
                storage[j].addActionListener(listener);
                storageButtonGroup.add(storage[j]); // 라디오 버튼을 그룹에 추가
                // x라는 글자가 포함되어 있다면 해당 라디오버튼을 선택할 수 없도록 설정
                if (deviceGalaxyPad[idn].storageOptions[j].contains("x")) {
                    storage[j].setEnabled(false);
                }
                storageRadioPanel.add(storage[j]);

            }
            for (int j = 0; j < 6; j++) {
                color[j] = new JRadioButton(deviceGalaxyPad[idn].colorOptions[j]);
                color[j].addActionListener(listener);
                colorButtonGroup.add(color[j]); // 라디오 버튼을 그룹에 추가
                // x라는 글자가 포함되어 있다면 해당 라디오버튼을 선택할 수 없도록 설정
                if (deviceGalaxyPad[idn].colorOptions[j].contains("x")) {
                    color[j].setEnabled(false);
                }
                colorRadioPanel.add(color[j]);
            }
        }



        // 새로운 프레임을 보이도록 설정
        newFrame.setSize(450, 500);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }
    // 라디오 그룹에서 선택된 텍스트를 가져오는 메소드
    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    //이미지 크기를 자동 설정해주는 메소드
    public ImageIcon resizeImage(ImageIcon im,int x,int y) {
        Image originalImage = im.getImage();
        Image resizedImage = originalImage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }
    // 영수증 프레임 생성 및 테이블 내용 추가 메소드
    public void generateReceiptFrame() {
        // 현재 날짜 및 시간을 얻기 위한 SimpleDateFormat 사용
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        // 새로운 프레임 생성
        JFrame receiptFrame = new JFrame("영수증");
        receiptFrame.setLayout(new BorderLayout());

        // 영수증 헤더
        String headerHtml = "<html><div style='text-align: center; font-size: 16px; font-weight: bold;'>====== 영수증 ======</div></html>";
        JLabel headerLabel = new JLabel(headerHtml, SwingConstants.CENTER);
        receiptFrame.add(headerLabel, BorderLayout.NORTH);

        // 영수증 내용을 담을 JTextPane (HTML을 지원하는 Swing 컴포넌트)
        JTextPane receiptTextPane = new JTextPane();
        receiptTextPane.setContentType("text/html");
        receiptTextPane.setEditable(false); // 편집 불가능하도록 설정
        receiptFrame.add(new JScrollPane(receiptTextPane), BorderLayout.CENTER);

        // 영수증에 내용 추가 (테이블 형식)
        int rowCount = tableModel.getRowCount();
        int receiptSum = 0;
        StringBuilder receiptHtml = new StringBuilder("<html>");
        receiptHtml.append("<table style='width:100%; border-collapse: collapse;'>");
        receiptHtml.append("<tr>");
        receiptHtml.append("<th style='border: none; padding: 8px; font-weight: bold; text-align: center;'>제품명</th>");
        receiptHtml.append("<th style='border: none; padding: 8px; font-weight: bold; text-align: center;'>용량</th>");
        receiptHtml.append("<th style='border: none; padding: 8px; font-weight: bold; text-align: center;'>색상</th>");
        receiptHtml.append("<th style='border: none; padding: 8px; font-weight: bold; text-align: center;'>수량</th>");
        receiptHtml.append("<th style='border: none; padding: 8px; font-weight: bold; text-align: center;'>가격</th>");

        receiptHtml.append("</tr>");

        for (int i = 0; i < rowCount; i++) {
            String deviceName = (String) tableModel.getValueAt(i, 0);
            String selectedStorage = (String) tableModel.getValueAt(i, 1);
            String selectedColor = (String) tableModel.getValueAt(i, 2);
            int selectedCount = (int) tableModel.getValueAt(i, 3);
            int totalPrice = (int) tableModel.getValueAt(i, 4);

            receiptHtml.append("<tr>");
            receiptHtml.append(String.format("<td style='none: 1px solid black; padding: 8px; text-align: center;'>%s</td>", deviceName));
            receiptHtml.append(String.format("<td style='none: 1px solid black; padding: 8px; text-align: center;'>%s</td>", selectedStorage));
            receiptHtml.append(String.format("<td style='none: 1px solid black; padding: 8px; text-align: center;'>%s</td>", selectedColor));
            receiptHtml.append(String.format("<td style='none: 1px solid black; padding: 8px; text-align: center;'>%d</td>", selectedCount));
            receiptHtml.append(String.format("<td style='none: 1px solid black; padding: 8px; text-align: center;'>%s원</td>", decimalFormat.format(totalPrice)));
            receiptHtml.append("</tr>");

            receiptSum += totalPrice;
        }
        receiptHtml.append("</table>");

        receiptHtml.append("<br>");
        receiptHtml.append(String.format("<div>총 가격: %s 원</div>", decimalFormat.format(receiptSum)));
        receiptHtml.append("<br>");
        receiptHtml.append("<div>===================</div>");
        receiptHtml.append(String.format("<div style='font-size: 12px;'>결제일시: %s</div>", currentDate));
        receiptHtml.append("</html>");

        receiptTextPane.setText(receiptHtml.toString());

        // 프레임 설정
        receiptFrame.setSize(800, 400);
        receiptFrame.setLocationRelativeTo(null); // 화면 중앙에 위치
        receiptFrame.setVisible(true);

        exportToCsv();
    }
    //CSV파일로 결제내역 저장하는 코드
    public void exportToCsv() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CSV_FILE_PATH), StandardCharsets.UTF_8))) {
            // BOM 쓰기
            writer.write('\ufeff');

            // 헤더 쓰기
            writer.write("제품명,용량,색상,수량,가격\n");

            // 데이터 쓰기
            int rowCount = tableModel.getRowCount();
            int receiptSum = 0;

            for (int i = 0; i < rowCount; i++) {
                String deviceName = (String) tableModel.getValueAt(i, 0);
                String selectedStorage = (String) tableModel.getValueAt(i, 1);
                String selectedColor = (String) tableModel.getValueAt(i, 2);
                int selectedCount = (int) tableModel.getValueAt(i, 3);
                int totalPrice = (int) tableModel.getValueAt(i, 4);

                writer.write(String.format("%s,%s,%s,%d,%s원%n",
                        deviceName, selectedStorage, selectedColor, selectedCount, totalPrice));

                receiptSum += totalPrice;
            }

            // 총 가격 쓰기
            writer.write("\n");
            writer.write(String.format("총 가격:, %s 원%n", receiptSum));
            writer.write("\n");

            // 푸터 쓰기
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate = dateFormat.format(new Date());
            writer.write("===================\n");
            writer.write(String.format("결제일시: %s", currentDate));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //테이블에 같은 내역이 있으면 가격과 수량을 업데이트하는 코드
    private void updateExistingRow(String deviceName, String selectedStorage, String selectedColor, int selectedCount, int totalPrice) {
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String existingDeviceName = (String) tableModel.getValueAt(i, 0);
            String existingStorage = (String) tableModel.getValueAt(i, 1);
            String existingColor = (String) tableModel.getValueAt(i, 2);

            // 기존 행과 새로운 데이터가 모두 일치하는 경우 수량과 가격을 누적 업데이트
            if (existingDeviceName.equals(deviceName) && existingStorage.equals(selectedStorage) && existingColor.equals(selectedColor)) {
                int existingCount = (int) tableModel.getValueAt(i, 3);
                int existingTotalPrice = (int) tableModel.getValueAt(i, 4);

                // 수량 및 가격 누적 업데이트
                tableModel.setValueAt(existingCount + selectedCount, i, 3);
                tableModel.setValueAt(existingTotalPrice + totalPrice, i, 4);
                return; // 업데이트가 완료되었으므로 메소드 종료
            }
        }

        // 테이블에 해당 제품이 없는 경우 새로운 행 추가
        tableModel.addRow(new Object[]{deviceName, selectedStorage, selectedColor, selectedCount, totalPrice, false});
    }

}
