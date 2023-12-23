# coding: utf-8
#
import uiautomator2 as u2

d = u2.connect()

d(resourceId="org.totschnig.myexpenses.debug:id/suw_navbar_next").click()
d(resourceId="org.totschnig.myexpenses.debug:id/suw_navbar_next").click()
d(resourceId="org.totschnig.myexpenses.debug:id/suw_navbar_done").click()
d(description="Open navigation drawer").click()
d(resourceId="org.totschnig.myexpenses.debug:id/headerIndicator").click()
d(resourceId="org.totschnig.myexpenses.debug:id/design_menu_item_text", text="New account").click()
d(resourceId="org.totschnig.myexpenses.debug:id/Currency").click()
d(resourceId="android:id/text1", text="Gold").click()
d(resourceId="org.totschnig.myexpenses.debug:id/Label").click()
d.send_keys("Gold", clear=True)
d(resourceId="org.totschnig.myexpenses.debug:id/Calculator", description="Goal / Limit : Calculator").click()
d(resourceId="org.totschnig.myexpenses.debug:id/MANAGE_TEMPLATES_COMMAND").click()
d(resourceId="org.totschnig.myexpenses.debug:id/CREATE_COMMAND").click()
d(resourceId="org.totschnig.myexpenses.debug:id/OperationType").click()
d(resourceId="android:id/text1", text="New transfer template").click()
d(resourceId="org.totschnig.myexpenses.debug:id/CREATE_COMMAND").click()
d(resourceId="org.totschnig.myexpenses.debug:id/Title").click()
d.send_keys("test", clear=True)
d(resourceId="org.totschnig.myexpenses.debug:id/CREATE_COMMAND").click()