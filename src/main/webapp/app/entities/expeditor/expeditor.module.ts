import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { ExpeditorComponent } from './expeditor.component';
import { ExpeditorDetailComponent } from './expeditor-detail.component';
import { ExpeditorUpdateComponent } from './expeditor-update.component';
import { ExpeditorDeleteDialogComponent } from './expeditor-delete-dialog.component';
import { expeditorRoute } from './expeditor.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(expeditorRoute)],
  declarations: [ExpeditorComponent, ExpeditorDetailComponent, ExpeditorUpdateComponent, ExpeditorDeleteDialogComponent],
  entryComponents: [ExpeditorDeleteDialogComponent],
})
export class OrmvahExpeditorModule {}
