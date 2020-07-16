import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { LeServiceComponent } from './le-service.component';
import { LeServiceDetailComponent } from './le-service-detail.component';
import { LeServiceUpdateComponent } from './le-service-update.component';
import { LeServiceDeleteDialogComponent } from './le-service-delete-dialog.component';
import { leServiceRoute } from './le-service.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(leServiceRoute)],
  declarations: [LeServiceComponent, LeServiceDetailComponent, LeServiceUpdateComponent, LeServiceDeleteDialogComponent],
  entryComponents: [LeServiceDeleteDialogComponent],
})
export class OrmvahLeServiceModule {}
