/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OrmvahTestModule } from '../../../test.module';
import { NatureCourrierDeleteDialogComponent } from 'app/entities/nature-courrier/nature-courrier-delete-dialog.component';
import { NatureCourrierService } from 'app/entities/nature-courrier/nature-courrier.service';

describe('Component Tests', () => {
  describe('NatureCourrier Management Delete Component', () => {
    let comp: NatureCourrierDeleteDialogComponent;
    let fixture: ComponentFixture<NatureCourrierDeleteDialogComponent>;
    let service: NatureCourrierService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [NatureCourrierDeleteDialogComponent]
      })
        .overrideTemplate(NatureCourrierDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NatureCourrierDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NatureCourrierService);
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
