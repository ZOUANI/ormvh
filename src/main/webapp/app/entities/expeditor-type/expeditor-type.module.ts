import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { ExpeditorTypeComponent } from './expeditor-type.component';
import { ExpeditorTypeDetailComponent } from './expeditor-type-detail.component';
import { ExpeditorTypeUpdateComponent } from './expeditor-type-update.component';
import { ExpeditorTypeDeleteDialogComponent } from './expeditor-type-delete-dialog.component';
import { expeditorTypeRoute } from './expeditor-type.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(expeditorTypeRoute)],
  declarations: [ExpeditorTypeComponent, ExpeditorTypeDetailComponent, ExpeditorTypeUpdateComponent, ExpeditorTypeDeleteDialogComponent],
  entryComponents: [ExpeditorTypeDeleteDialogComponent],
})
export class OrmvahExpeditorTypeModule {}
