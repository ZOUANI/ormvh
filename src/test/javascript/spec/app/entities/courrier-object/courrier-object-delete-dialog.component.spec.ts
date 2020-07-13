/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OrmvahTestModule } from '../../../test.module';
import { CourrierObjectDeleteDialogComponent } from 'app/entities/courrier-object/courrier-object-delete-dialog.component';
import { CourrierObjectService } from 'app/entities/courrier-object/courrier-object.service';

describe('Component Tests', () => {
  describe('CourrierObject Management Delete Component', () => {
    let comp: CourrierObjectDeleteDialogComponent;
    let fixture: ComponentFixture<CourrierObjectDeleteDialogComponent>;
    let service: CourrierObjectService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CourrierObjectDeleteDialogComponent]
      })
        .overrideTemplate(CourrierObjectDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CourrierObjectDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CourrierObjectService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
