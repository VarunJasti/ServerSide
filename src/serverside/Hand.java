package serverside;

import java.util.ArrayList;
import java.util.Collections;

public class Hand implements Comparable {

    private ArrayList<Card> cards = new ArrayList<>();
    private Card[] special = new Card[5];
    private Card[] normal = new Card[5];
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public String toString() {
        String s = "";
        for (int i = 0; i < cards.size(); i++) {
            if (i == 0) {
                s += cards.get(i).toString();
            } else {
                s += ", " + cards.get(i).toString();
            }
        }
        return s;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Hand) {
            Hand h = (Hand) o;
            if (this.isRoyalFlush() && !h.isRoyalFlush()) {
                return -1;
            } else if (!this.isRoyalFlush() && h.isRoyalFlush()) {
                return 1;
            } else if (this.isRoyalFlush() && h.isRoyalFlush()) {
                if (cards.get(0).getSuit() > h.getCards().get(0).getSuit()) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (this.isStraightFlush() && !h.isStraightFlush()) {
                    return -1;
                } else if (!this.isStraightFlush() && h.isStraightFlush()) {
                    return 1;
                } else if (this.isStraightFlush() && h.isStraightFlush()) {
                    if (cards.get(1).getCMarking() > h.getCards().get(1).getCMarking()) {
                        return -1;
                    } else if (cards.get(1).getCMarking() < h.getCards().get(1).getCMarking()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (this.is4s() && !h.is4s()) {
                        return -1;
                    } else if (!this.is4s() && h.is4s()) {
                        return 1;
                    } else if (this.is4s() && h.is4s()) {
                        int n = special[0].compareTo(h.getSpecial()[0]);
                        if (n != 0) {
                            return n;
                        } else {
                            return normal[0].compareTo(h.getNormal()[0]);
                        }
                    } else {
                        if (this.isFullHouse() && !h.isFullHouse()) {
                            return -1;
                        } else if (!this.isFullHouse() && h.isFullHouse()) {
                            return 1;
                        } else if (this.isFullHouse() && h.isFullHouse()) {
                            int n = special[0].compareTo(h.getSpecial()[0]);
                            if (n != 0) {
                                return n;
                            } else {
                                return special[1].compareTo(h.getSpecial()[1]);
                            }
                        } else {
                            if (this.isFlush() && !h.isFlush()) {
                                return -1;
                            } else if (!this.isFlush() && h.isFlush()) {
                                return 1;
                            } else if (this.isFlush() && h.isFlush()) {
                                return new Card(cards.get(0).getCMarking(), 0).compareTo(new Card(h.getCards().get(0).getCMarking(), 0));
                            } else {
                                if (this.isStraight() && !h.isStraight()) {
                                    return -1;
                                } else if (!this.isStraight() && h.isStraight()) {
                                    return 1;
                                } else if (this.isStraight() && h.isStraight()) {
                                    return special[0].compareTo(h.getSpecial()[0]);
                                } else {
                                    if (this.is22s() && !h.is22s()) {
                                        return -1;
                                    } else if (!this.is22s() && h.is22s()) {
                                        return 1;
                                    } else if (this.is22s() && h.is22s()) {
                                        int n = special[0].compareTo(h.getSpecial()[0]);
                                        if (n != 0) {
                                            return n;
                                        } else {
                                            n = special[1].compareTo(h.getSpecial()[1]);
                                            if (n != 0) {
                                                return n;
                                            } else {
                                                return normal[0].compareTo(h.getNormal()[0]);
                                            }
                                        }
                                    } else {
                                        if (this.is2s() && !h.is2s()) {
                                            return -1;
                                        } else if (!this.is2s() && h.is2s()) {
                                            return 1;
                                        } else if (this.is2s() && h.is2s()) {
                                            int n = special[0].compareTo(h.getSpecial()[0]);
                                            if (n != 0) {
                                                return n;
                                            } else {
                                                n = normal[0].compareTo(h.getNormal()[0]);
                                                if (n != 0) {
                                                    return n;
                                                } else {
                                                    n = normal[1].compareTo(h.getNormal()[1]);
                                                    if (n != 0) {
                                                        return n;
                                                    } else {
                                                        return normal[2].compareTo(h.getNormal()[2]);
                                                    }
                                                }
                                            }
                                        } else {
                                            ArrayList<Card> temp1 = new ArrayList<>();
                                            for (Card c : cards) {
                                                temp1.add(new Card(c.getCMarking(), 0));
                                            }
                                            Collections.sort(temp1);
                                            ArrayList<Card> temp2 = new ArrayList<>();
                                            for (Card c : h.getCards()) {
                                                temp2.add(new Card(c.getCMarking(), 0));
                                            }
                                            Collections.sort(temp2);
                                            int n = temp1.get(0).compareTo(temp2.get(0));
                                            if (n != 0) {
                                                return n;
                                            } else {
                                                n = temp1.get(1).compareTo(temp2.get(1));
                                                if (n != 0) {
                                                    return n;
                                                } else {
                                                    n = temp1.get(2).compareTo(temp2.get(2));
                                                    if (n != 0) {
                                                        return n;
                                                    } else {
                                                        n = temp1.get(3).compareTo(temp2.get(3));
                                                        if (n != 0) {
                                                            return n;
                                                        } else {
                                                            return temp1.get(4).compareTo(temp2.get(4));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public Card[] getSpecial() {
        return special;
    }

    public Card[] getNormal() {
        return normal;
    }

    public boolean is2s() {
        if (cards.size() != 5) {
            return false;
        }
        if (is4s() || isFullHouse() || is3s() || is22s()) {
            return false;
        }
        ArrayList<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(new Card(c.getCMarking(), 0));
        }
        Collections.sort(temp);
        boolean a = temp.get(0).getCMarking() == temp.get(1).getCMarking();
        boolean b = temp.get(1).getCMarking() == temp.get(2).getCMarking();
        boolean c = temp.get(2).getCMarking() == temp.get(3).getCMarking();
        boolean d = temp.get(3).getCMarking() == temp.get(4).getCMarking();
        if (a) {
            special[0] = temp.get(0);
            normal[0] = temp.get(2);
            normal[1] = temp.get(3);
            normal[2] = temp.get(4);
        } else if (b) {
            special[0] = temp.get(1);
            normal[0] = temp.get(0);
            normal[1] = temp.get(3);
            normal[2] = temp.get(4);
        } else if (c) {
            special[0] = temp.get(2);
            normal[0] = temp.get(0);
            normal[1] = temp.get(1);
            normal[2] = temp.get(4);
        } else if (d) {
            special[0] = temp.get(3);
            normal[0] = temp.get(0);
            normal[1] = temp.get(1);
            normal[2] = temp.get(2);
        }
        return (a || b || c || d);
    }

    public boolean is22s() {
        if (cards.size() != 5) {
            return false;
        }
        if (is4s() || isFullHouse() || is3s()) {
            return false;
        }
        ArrayList<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(new Card(c.getCMarking(), 0));
        }
        Collections.sort(temp);
        boolean a = temp.get(0).getCMarking() == temp.get(1).getCMarking()
                && temp.get(2).getCMarking() == temp.get(3).getCMarking();
        boolean b = temp.get(0).getCMarking() == temp.get(1).getCMarking()
                && temp.get(3).getCMarking() == temp.get(4).getCMarking();
        boolean c = temp.get(1).getCMarking() == temp.get(2).getCMarking()
                && temp.get(3).getCMarking() == temp.get(4).getCMarking();
        if (a) {
            special[0] = temp.get(0);
            special[1] = temp.get(2);
            normal[0] = temp.get(4);
        } else if (b) {
            special[0] = temp.get(0);
            special[1] = temp.get(4);
            normal[0] = temp.get(2);
        } else if (c) {
            special[0] = temp.get(2);
            special[1] = temp.get(4);
            normal[0] = temp.get(0);
        }
        return (a || b || c);
    }

    public boolean is3s() {
        if (cards.size() != 5) {
            return false;
        }
        if (is4s() || isFullHouse()) {
            return false;
        }
        ArrayList<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(new Card(c.getCMarking(), 0));
        }
        Collections.sort(temp);
        boolean a = temp.get(0).getCMarking() == temp.get(1).getCMarking()
                && temp.get(1).getCMarking() == temp.get(2).getCMarking();
        boolean b = temp.get(1).getCMarking() == temp.get(2).getCMarking()
                && temp.get(2).getCMarking() == temp.get(3).getCMarking();
        boolean c = temp.get(2).getCMarking() == temp.get(3).getCMarking()
                && temp.get(3).getCMarking() == temp.get(4).getCMarking();
        return (a || b || c);
    }

    public boolean isFullHouse() {
        if (cards.size() != 5) {
            return false;
        }
        ArrayList<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(new Card(c.getCMarking(), 0));
        }
        Collections.sort(temp);
        boolean a = temp.get(0).getCMarking() == temp.get(1).getCMarking()
                && temp.get(1).getCMarking() == temp.get(2).getCMarking()
                && temp.get(3).getCMarking() == temp.get(4).getCMarking();
        boolean b = temp.get(0).getCMarking() == temp.get(1).getCMarking()
                && temp.get(2).getCMarking() == temp.get(3).getCMarking()
                && temp.get(3).getCMarking() == temp.get(4).getCMarking();
        if (a) {
            special[0] = temp.get(0);
            special[1] = temp.get(4);
        } else if (b) {
            special[0] = temp.get(4);
            special[1] = temp.get(0);
        }
        return (a || b);
    }

    public boolean is4s() {
        if (cards.size() != 5) {
            return false;
        }
        ArrayList<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(new Card(c.getCMarking(), 0));
        }
        Collections.sort(temp);
        boolean a = temp.get(0).getCMarking() == temp.get(1).getCMarking()
                && temp.get(1).getCMarking() == temp.get(2).getCMarking()
                && temp.get(2).getCMarking() == temp.get(3).getCMarking();
        boolean b = temp.get(1).getCMarking() == temp.get(2).getCMarking()
                && temp.get(2).getCMarking() == temp.get(3).getCMarking()
                && temp.get(3).getCMarking() == temp.get(4).getCMarking();
        if (a) {
            special[0] = temp.get(0);
            normal[0] = temp.get(4);
        } else if (b) {
            special[0] = temp.get(1);
            normal[0] = temp.get(0);
        }
        return (a || b);
    }

    public boolean isFlush() {
        Collections.sort(cards);
        return cards.get(0).getSuit() == cards.get(cards.size() - 1).getSuit();
    }

    public boolean isStraight() {
        ArrayList<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(new Card(c.getCMarking(), 0));
        }
        Collections.sort(temp);
        int n = temp.get(temp.size() - 1).getCMarking();
        if (temp.get(0).getCMarking() == 1 && (temp.get(1).getCMarking() == 13 || temp.get(1).getCMarking() == 5)) {
            for (int i = temp.size() - 2; i >= 1; i--) {
                if (!(temp.get(i).getCMarking() == n + 1)) {
                    return false;
                }
                n++;
            }
        } else {
            for (int i = temp.size() - 2; i >= 0; i--) {
                if (!(temp.get(i).getCMarking() == n + 1)) {
                    return false;
                }
                n++;
            }
        }
        special[0] = temp.get(1);
        return true;
    }

    public boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    public boolean isRoyalFlush() {
        return isStraightFlush() && cards.get(0).getCMarking() == 1 && cards.get(1).getCMarking() == 13;
    }

}
