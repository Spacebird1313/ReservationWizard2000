package GUI;

import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class HelpDialog //Easter-egg
{
	public HelpDialog(Image RW2000Image)
	{
		JOptionPane pane = new JOptionPane("Heeft U hulp nodig?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		JDialog dialog = pane.createDialog("Help");
		dialog.setIconImage(RW2000Image);	
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		if(pane.getValue() != null)
		{
			if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
			{
				pane = new JOptionPane("Bent U zeker?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
				dialog = pane.createDialog("Help");
				dialog.setIconImage(RW2000Image);	
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				if(pane.getValue() != null)
				{
					if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
					{
						pane = new JOptionPane("Weet U het absoluut zeker?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
						dialog = pane.createDialog("Help");
						dialog.setIconImage(RW2000Image);	
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						if(pane.getValue() != null)
						{						
							if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
							{
								pane = new JOptionPane("Dan Vrees ik dat ik u moet teleurstellen.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
								dialog = pane.createDialog("Help");
								dialog.setIconImage(RW2000Image);	
								dialog.setAlwaysOnTop(true);
								dialog.setVisible(true);
								if(pane.getValue() != null)
								{
									if(((Integer)pane.getValue()).intValue() == JOptionPane.OK_OPTION)
									{
										pane = new JOptionPane("Want er is geen help optie.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
										dialog = pane.createDialog("Help");
										dialog.setIconImage(RW2000Image);	
										dialog.setAlwaysOnTop(true);
										dialog.setVisible(true);
										if(pane.getValue() != null)
										{
											if(((Integer)pane.getValue()).intValue() == JOptionPane.OK_OPTION)
											{
												pane = new JOptionPane("Alles is op zichzelf al duidelijk.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
												dialog = pane.createDialog("Help");
												dialog.setIconImage(RW2000Image);	
												dialog.setAlwaysOnTop(true);
												dialog.setVisible(true);
												if(pane.getValue() != null)
												{
													if(((Integer)pane.getValue()).intValue() == JOptionPane.OK_OPTION)
													{
														pane = new JOptionPane("Veel succes.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
														dialog = pane.createDialog("Help");
														dialog.setIconImage(RW2000Image);	
														dialog.setAlwaysOnTop(true);
														dialog.setVisible(true);
													}
												}
												else
													weggeklikt(RW2000Image);
											}
										}
										else
											weggeklikt(RW2000Image);
									}
								}
								else
									weggeklikt(RW2000Image);
							}
							else
							{
								pane = new JOptionPane("Jij bent wel een obeslist type nietwaar?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
								dialog = pane.createDialog("Help");
								dialog.setIconImage(RW2000Image);	
								dialog.setAlwaysOnTop(true);
								dialog.setVisible(true);
								if(pane.getValue() != null)
								{
									
									pane = new JOptionPane("Appelen of peren?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
									dialog = pane.createDialog("Help");
									dialog.setIconImage(RW2000Image);	
									dialog.setAlwaysOnTop(true);
									dialog.setVisible(true);
									if(pane.getValue() != null)
									{
										pane = new JOptionPane("Ha, Strikvraag. Je kon niets juist aanklikken >:)", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
										dialog = pane.createDialog("Help");
										dialog.setIconImage(RW2000Image);	
										dialog.setAlwaysOnTop(true);
										dialog.setVisible(true);
										if(pane.getValue() != null)
										{
											
										}
										else
										{
											weggeklikt(RW2000Image);
										}
									}
									else
									{
										weggeklikt(RW2000Image);
									}
								}
								else
								{
									weggeklikt(RW2000Image);
								}
							}
						}
						else
						{
							weggeklikt(RW2000Image);
						}
					}
					else
					{
						pane = new JOptionPane("Oh, fout geklikt waarschijnlijk?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
						dialog = pane.createDialog("Help");
						dialog.setIconImage(RW2000Image);	
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						if(pane.getValue() != null)
						{
							if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
							{
								pane = new JOptionPane("In dat geval zal ik U niet verder lastigvallen", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
								dialog = pane.createDialog("Help");
								dialog.setIconImage(RW2000Image);	
								dialog.setAlwaysOnTop(true);
								dialog.setVisible(true);
								if(pane.getValue() != null)
								{

								}
								else
								{
									weggeklikt(RW2000Image);
								}
							}
							else
							{
								pane = new JOptionPane("U klikte dus bewust op de knop?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
								dialog = pane.createDialog("Help");
								dialog.setIconImage(RW2000Image);	
								dialog.setAlwaysOnTop(true);
								dialog.setVisible(true);
								if(pane.getValue() != null)
								{
									if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
									{
										pane = new JOptionPane("Klikte u dan uit nieuwschierigheid?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
										dialog = pane.createDialog("Help");
										dialog.setIconImage(RW2000Image);	
										dialog.setAlwaysOnTop(true);
										dialog.setVisible(true);
										if(pane.getValue() != null)
										{
											if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
											{
												pane = new JOptionPane("Wel U heeft wat u wou.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION);
												dialog = pane.createDialog("Help");
												dialog.setIconImage(RW2000Image);	
												dialog.setAlwaysOnTop(true);
												dialog.setVisible(true);
												if(pane.getValue() != null)
												{
													if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
													{
														pane = new JOptionPane("Dan ga ik u nu laten", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
														dialog = pane.createDialog("Help");
														dialog.setIconImage(RW2000Image);	
														dialog.setAlwaysOnTop(true);
														dialog.setVisible(true);
														if(pane.getValue() != null)
														{
															
														}
														else
														{
															weggeklikt(RW2000Image);
														}
													}
													else
													{
														pane = new JOptionPane("Arme ziel toch.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
														dialog = pane.createDialog("Help");
														dialog.setIconImage(RW2000Image);	
														dialog.setAlwaysOnTop(true);
														dialog.setVisible(true);
														if(pane.getValue() != null)
														{

														}
														else
														{
															weggeklikt(RW2000Image);
														}
													}
												}
												else
												{
													weggeklikt(RW2000Image);
												}
											}
											else
											{
												pane = new JOptionPane("Ik wordt gek van mensen zoals u.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
												dialog = pane.createDialog("Help");
												dialog.setIconImage(RW2000Image);	
												dialog.setAlwaysOnTop(true);
												dialog.setVisible(true);
												if(pane.getValue() != null)
												{
													pane = new JOptionPane("Hallo daar meneer de roze olifant.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
													dialog = pane.createDialog("Help");
													dialog.setIconImage(RW2000Image);	
													dialog.setAlwaysOnTop(true);
													dialog.setVisible(true);
													if(pane.getValue() != null)
													{
														pane = new JOptionPane("Hoe stelt u het.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
														dialog = pane.createDialog("Help");
														dialog.setIconImage(RW2000Image);	
														dialog.setAlwaysOnTop(true);
														dialog.setVisible(true);
														if(pane.getValue() != null)
														{
															pane = new JOptionPane("Ik voel me ççà&^ù$'.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
															dialog = pane.createDialog("Help");
															dialog.setIconImage(RW2000Image);	
															dialog.setAlwaysOnTop(true);
															dialog.setVisible(true);
															if(pane.getValue() != null)
															{
																pane = new JOptionPane("En hoe voelt U zich.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
																dialog = pane.createDialog("Help");
																dialog.setIconImage(RW2000Image);	
																dialog.setAlwaysOnTop(true);
																dialog.setVisible(true);
																if(pane.getValue() != null)
																{
																	pane = new JOptionPane("-Miauw-", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
																	dialog = pane.createDialog("Help");
																	dialog.setIconImage(RW2000Image);	
																	dialog.setAlwaysOnTop(true);
																	dialog.setVisible(true);
																	if(pane.getValue() != null)
																	{
																		pane = new JOptionPane("ERROR.", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
																		dialog = pane.createDialog("Help");
																		dialog.setIconImage(RW2000Image);	
																		dialog.setAlwaysOnTop(true);
																		dialog.setVisible(true);
																		if(pane.getValue() != null)
																		{
																			
																		}
																		else
																		{
																			weggeklikt(RW2000Image);
																		}
																	}
																	else
																	{
																		weggeklikt(RW2000Image);
																	}
																}
																else
																{
																	weggeklikt(RW2000Image);
																}
															}
															else
															{
																weggeklikt(RW2000Image);
															}
														}
														else
														{
															weggeklikt(RW2000Image);
														}
													}
													else
													{
														weggeklikt(RW2000Image);
													}
												}
												else
												{
													weggeklikt(RW2000Image);
												}
											}
										}
										else
										{
											weggeklikt(RW2000Image);
										}
									}
									else
									{
										pane = new JOptionPane("Ik snap mensen zoals u niet. Dus tot ziens.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
										dialog = pane.createDialog("Help");
										dialog.setIconImage(RW2000Image);	
										dialog.setAlwaysOnTop(true);
										dialog.setVisible(true);
										if(pane.getValue() != null)
										{

										}
										else
										{
											weggeklikt(RW2000Image);
										}
									}
								}
								else
								{
									weggeklikt(RW2000Image);
								}
							}
						}
						else
						{
							weggeklikt(RW2000Image);
						}
					}
				}
				else
				{
					weggeklikt(RW2000Image);
				}
			}
			else
			{
				pane = new JOptionPane("fout geklikt waarschijnlijk?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
				dialog = pane.createDialog("Help");
				dialog.setIconImage(RW2000Image);	
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				if(pane.getValue() != null)
				{
					if(((Integer)pane.getValue()).intValue() == JOptionPane.OK_OPTION)
					{
						pane = new JOptionPane("Het is U vergeven.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
						dialog = pane.createDialog("Help");
						dialog.setIconImage(RW2000Image);	
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					}
					else
					{
						pane = new JOptionPane("HOE! HOE kAN JE NU VERKEERD KLIKKEN? DE KNOPPEN ZIJN GIGANTISCH! Ik hoop dat dit niet nog eens gebeurt want dan gaan er mensen spijt hebben.", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
						dialog = pane.createDialog("Help");
						dialog.setIconImage(RW2000Image);	
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					}
				}
				else
					weggeklikt(RW2000Image);
			}
		}
		else
		{
			weggeklikt(RW2000Image);		
		}
	}
	
	public void weggeklikt(Image RW2000Image)
	{
		JOptionPane pane = new JOptionPane("Heeft U mij net echt weggeklikt!?!", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION);
		JDialog dialog = pane.createDialog("Error");
		dialog.setIconImage(RW2000Image);	
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		if(pane.getValue() != null)
		{
			if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
			{
				pane = new JOptionPane("U bent een eerlijk man.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				dialog = pane.createDialog("Error");
				dialog.setIconImage(RW2000Image);	
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				if(pane.getValue() != null)
				{
					if(((Integer)pane.getValue()).intValue() == JOptionPane.OK_OPTION)
					{
						
					}
				}
			}
			else
			{
				pane = new JOptionPane("Nice Try", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				dialog = pane.createDialog("Help");
				dialog.setIconImage(RW2000Image);	
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				if(pane.getValue() != null)
				{
					if(((Integer)pane.getValue()).intValue() == JOptionPane.OK_OPTION)
					{
						
					}
				}
			}
		}
		else
		{
			weggeklikt2(RW2000Image);
		}
	}
	
	public void cancelled(Image RW2000Image)
	{
		JOptionPane pane = new JOptionPane("Oh, fout geklikt waarschijnlijk?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		JDialog dialog = pane.createDialog("Help");
		dialog.setIconImage(RW2000Image);	
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		if(pane.getValue() != null)
		{
			if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
			{
				
			}
		}
	}
	
	public void weggeklikt2(Image RW2000Image)
	{
		JOptionPane pane = new JOptionPane("Serieus? Nog eens Wegklikken?", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION);
		JDialog dialog = pane.createDialog("Error");
		dialog.setIconImage(RW2000Image);	
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		if(pane.getValue() != null)
		{
			if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
			{
				
			}
		}
		else
			weggeklikt3(RW2000Image);
	}
	
	public void weggeklikt3(Image RW2000Image)
	{
		JOptionPane pane = new JOptionPane("Ok, Genoeg. Probeer deze maar eens weg te klikken.", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = pane.createDialog("Error");
		dialog.setIconImage(RW2000Image);	
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		if(pane.getValue() != null)
		{
			pane = new JOptionPane("Ok, ok. Jij wint.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
			dialog = pane.createDialog("Help");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
		else
		{
			pane = new JOptionPane("Ok, ok. Jij wint.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
			dialog = pane.createDialog("Help");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
	}
}
