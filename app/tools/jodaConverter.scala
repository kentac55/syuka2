package tools

/**
  * Created by kentac55 on 16/03/25.
  */

// http://qiita.com/mather314/items/1d0e3bb2e94283f85e96

import java.sql.Timestamp
import org.joda.time.DateTime

trait jodaConverter {
  def dateTimeToSqlTimestamp: DateTime =>
    Timestamp = { dt => new Timestamp(dt.getMillis) }
  def sqlTimestampToDateTime: Timestamp =>
    DateTime = { ts => new DateTime(ts.getTime) }
}
object jodaConverter extends jodaConverter